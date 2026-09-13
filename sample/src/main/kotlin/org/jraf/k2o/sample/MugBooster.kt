/*
 * This source is part of the
 *      _____  ___   ____
 *  __ / / _ \/ _ | / __/___  _______ _
 * / // / , _/ __ |/ _/_/ _ \/ __/ _ `/
 * \___/_/|_/_/ |_/_/ (_)___/_/  \_, /
 *                              /___/
 * repository.
 *
 * Copyright (C) 2025-present Benoit 'BoD' Lubek (BoD@JRAF.org)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

@file:Suppress("SameParameterValue")

package org.jraf.k2o.sample

import androidx.compose.runtime.Composable
import org.jraf.k2o.dsl.openScad
import org.jraf.k2o.stdlib.Call
import org.jraf.k2o.stdlib.Color
import org.jraf.k2o.stdlib.Cube
import org.jraf.k2o.stdlib.Cylinder
import org.jraf.k2o.stdlib.Square
import org.jraf.k2o.stdlib.Use
import org.jraf.k2o.stdlib.color
import org.jraf.k2o.stdlib.difference
import org.jraf.k2o.stdlib.linearExtrude
import org.jraf.k2o.stdlib.rotate
import org.jraf.k2o.stdlib.rotateExtrude
import org.jraf.k2o.stdlib.translate
import org.jraf.k2o.stdlib.union
import org.jraf.k2o.units.Angle.Companion.deg
import org.jraf.k2o.units.Length
import org.jraf.k2o.units.Length.Companion.cm
import org.jraf.k2o.units.Length.Companion.mm
import org.jraf.k2o.units.Length.Companion.pythagoreanSide

@Composable
private fun Base(
  width: Length,
  thickness: Length,
  indentWidth: Length,
) {
  color(Color.Green) {
    difference {
      Cylinder(height = thickness, radius = width / 2)

      linearExtrude(thickness * 2) {
        Call(
          "star",
          "p" to 5,
          "r1" to width / 2 - indentWidth * 2,
          "r2" to (width / 2 - indentWidth * 2) / 2.25,
        )
      }
    }
  }
}

@Composable
private fun Leg(
  thickness: Length,
  width: Length,
  height: Length,
  curveRadius: Length,
) {
  translate(y = width, z = height - curveRadius) {
    rotate(x = 90.deg) {
      rotateExtrude(90.deg) {
        translate(x = curveRadius) {
          Square(thickness, width)
        }
      }
    }
  }

  translate(x = curveRadius) {
    Cube(thickness, width, height - curveRadius)
  }

  translate(x = -15.mm, z = height) {
    Cube(15.mm, width, thickness)
  }
}

@Composable
private fun MugBooster() {
  Use("star.scad")

  val baseWidth = 10.cm
  val baseThickness = 4.mm
  val baseHeight = 8.cm
  val baseIndentHeight = 1.25.mm
  val baseIndentWidth = 8.mm

  val legThickness = baseThickness

  val coffeeMachineBaseWidth = 13.cm

  // Distance between the coffee machine base and the legs
  val coffeeMachineBaseMargin = .5.mm

  // Distance on the X axis between the centers of two legs
  val legXDistance = coffeeMachineBaseWidth + legThickness + coffeeMachineBaseMargin * 2

  // Distance on the Y axis between the centers of two legs
  val legYDistance = 6.cm

  val legWidth = 8.mm

  val legCurveRadius = (legXDistance - baseThickness) / 2 - pythagoreanSide(
    hypotenuse = baseWidth / 2,
    side = (legYDistance - legWidth) / 2,
  )

  difference {
    union {
      // Base
      translate(z = baseHeight) {
        Base(width = baseWidth, thickness = baseThickness, indentWidth = baseIndentWidth)
      }

      // Right bottom leg
      translate(x = legXDistance / 2 - baseThickness / 2 - legCurveRadius, y = -legYDistance / 2 - legWidth / 2) {
        Leg(thickness = legThickness, width = legWidth, height = baseHeight, curveRadius = legCurveRadius)
      }

      // Right top leg
      translate(x = legXDistance / 2 - baseThickness / 2 - legCurveRadius, y = legYDistance / 2 - legWidth / 2) {
        Leg(thickness = legThickness, width = legWidth, height = baseHeight, curveRadius = legCurveRadius)
      }

      // Left top leg
      rotate(z = 180.deg) {
        translate(x = legXDistance / 2 - baseThickness / 2 - legCurveRadius, y = -legYDistance / 2 - legWidth / 2) {
          Leg(thickness = legThickness, width = legWidth, height = baseHeight, curveRadius = legCurveRadius)
        }
      }

      // Left bottom leg
      rotate(z = 180.deg) {
        translate(x = legXDistance / 2 - baseThickness / 2 - legCurveRadius, y = legYDistance / 2 - legWidth / 2) {
          Leg(thickness = legThickness, width = legWidth, height = baseHeight, curveRadius = legCurveRadius)
        }
      }
    }
    // Indent
    translate(z = baseHeight + baseThickness - baseIndentHeight) {
      Cylinder(height = baseIndentHeight * 2, radius = baseWidth / 2 - baseIndentWidth)
    }
  }
}

fun main() {
  openScad(
    fa = 0.1,
    fs = 0.1,
  ) {
    MugBooster()
  }
}
