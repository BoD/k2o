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
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.jraf.k2o.sample

import androidx.compose.runtime.Composable
import kotlinx.io.asSink
import kotlinx.io.buffered
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
import kotlin.math.pow
import kotlin.math.sqrt

@Composable
private fun Base(
  width: Int,
  thickness: Int,
  indentWidth: Int,
) {
  color(Color.GREEN) {
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
  thickness: Number,
  width: Number,
  height: Number,
  curveRadius: Number,
) {
  translate(y = width, z = height.toDouble() - curveRadius.toDouble()) {
    rotate(x = 90) {
      rotateExtrude(90) {
        translate(x = curveRadius) {
          Square(thickness, width)
        }
      }
    }
  }

  translate(x = curveRadius) {
    Cube(thickness, width, height.toDouble() - curveRadius.toDouble())
  }

  translate(x = -15, z = height) {
    Cube(15, width, thickness)
  }
}

@Composable
private fun MugBooster() {
  Use("star.scad")

  val baseWidth = 100
  val baseThickness = 4
  val baseHeight = 80
  val baseIndentHeight = 1.25
  val baseIndentWidth = 8

  val legThickness = baseThickness

  val coffeeMachineBaseWidth = 130

  // Distance between the coffee machine base and the legs
  val coffeeMachineBaseMargin = .5

  // Distance on the X axis between the centers of two legs
  val legXDistance = coffeeMachineBaseWidth + legThickness + coffeeMachineBaseMargin * 2

  // Distance on the Y axis between the centers of two legs
  val legYDistance = 60

  val legWidth = 8

  val legCurveRadius =
    (legXDistance - baseThickness) / 2 - sqrt((baseWidth / 2.0).pow(2) - ((legYDistance - legWidth) / 2.0).pow(2))

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
      rotate(z = 180) {
        translate(x = legXDistance / 2 - baseThickness / 2 - legCurveRadius, y = -legYDistance / 2 - legWidth / 2) {
          Leg(thickness = legThickness, width = legWidth, height = baseHeight, curveRadius = legCurveRadius)
        }
      }

      // Left bottom leg
      rotate(z = 180) {
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
  openScad(System.out.asSink().buffered()) {
    MugBooster()
  }
}
