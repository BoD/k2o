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

@file:Suppress("FunctionName")

package org.jraf.k2o.sample

import kotlinx.io.asSink
import kotlinx.io.buffered
import org.jraf.k2o.dsl.OpenScad
import org.jraf.k2o.dsl.writeOpenScad
import org.jraf.k2o.stdlib.Color
import org.jraf.k2o.stdlib.call
import org.jraf.k2o.stdlib.color
import org.jraf.k2o.stdlib.cube
import org.jraf.k2o.stdlib.cylinder
import org.jraf.k2o.stdlib.difference
import org.jraf.k2o.stdlib.linearExtrude
import org.jraf.k2o.stdlib.rotate
import org.jraf.k2o.stdlib.rotateExtrude
import org.jraf.k2o.stdlib.square
import org.jraf.k2o.stdlib.translate
import org.jraf.k2o.stdlib.union
import org.jraf.k2o.stdlib.use
import kotlin.math.pow
import kotlin.math.sqrt

private fun OpenScad.Base(
  width: Int,
  thickness: Int,
  indentWidth: Int,
) {
  color(Color.GREEN) {
    difference {
      cylinder(height = thickness, radius = width / 2)

      linearExtrude(thickness * 2) {
        call(
          "star",
          "p" to 5,
          "r1" to width / 2 - indentWidth * 2,
          "r2" to (width / 2 - indentWidth * 2) / 2.25,
        )
      }
    }
  }
}

private fun OpenScad.Leg(
  thickness: Number,
  width: Number,
  height: Number,
  curveRadius: Number,
) {
  translate(0, width, height.toDouble() - curveRadius.toDouble()) {
    rotate(90, 0, 0) {
      rotateExtrude(90) {
        translate(curveRadius, 0, 0) {
          square(thickness, width)
        }
      }
    }
  }

  translate(curveRadius, 0, 0) {
    cube(thickness, width, height.toDouble() - curveRadius.toDouble())
  }

  translate(-15, 0, height) {
    cube(15, width, thickness)
  }
}

private fun OpenScad.MugBooster() {
  use("star.scad")

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
      translate(0, 0, baseHeight) {
        Base(width = baseWidth, thickness = baseThickness, indentWidth = baseIndentWidth)
      }

      // Right bottom leg
      translate(legXDistance / 2 - baseThickness / 2 - legCurveRadius, -legYDistance / 2 - legWidth / 2, 0) {
        Leg(thickness = legThickness, width = legWidth, height = baseHeight, curveRadius = legCurveRadius)
      }

      // Right top leg
      translate(legXDistance / 2 - baseThickness / 2 - legCurveRadius, legYDistance / 2 - legWidth / 2, 0) {
        Leg(thickness = legThickness, width = legWidth, height = baseHeight, curveRadius = legCurveRadius)
      }

      // Left top leg
      rotate(0, 0, 180) {
        translate(legXDistance / 2 - baseThickness / 2 - legCurveRadius, -legYDistance / 2 - legWidth / 2, 0) {
          Leg(thickness = legThickness, width = legWidth, height = baseHeight, curveRadius = legCurveRadius)
        }
      }

      // Left bottom leg
      rotate(0, 0, 180) {
        translate(legXDistance / 2 - baseThickness / 2 - legCurveRadius, legYDistance / 2 - legWidth / 2, 0) {
          Leg(thickness = legThickness, width = legWidth, height = baseHeight, curveRadius = legCurveRadius)
        }
      }
    }
    // Indent
    translate(0, 0, baseHeight + baseThickness - baseIndentHeight) {
      cylinder(height = baseIndentHeight * 2, radius = baseWidth / 2 - baseIndentWidth)
    }
  }
}

fun main() {
  writeOpenScad(System.out.asSink().buffered()) {
    MugBooster()
  }
}
