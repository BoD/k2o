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

package org.jraf.k2o.main

import kotlinx.io.asSink
import kotlinx.io.buffered
import org.jraf.k2o.dsl.OpenScad
import org.jraf.k2o.dsl.writeOpenScad
import org.jraf.k2o.stdlib.call
import org.jraf.k2o.stdlib.circle
import org.jraf.k2o.stdlib.difference
import org.jraf.k2o.stdlib.rotate
import org.jraf.k2o.stdlib.rotateExtrude
import org.jraf.k2o.stdlib.square
import org.jraf.k2o.stdlib.translate
import org.jraf.k2o.stdlib.use

class PotPlate {
  private fun OpenScad.crossSection(
    diameter: Int,
    thickness: Int,
    borderHeight: Int,
    borderAngle: Int,
  ) {
    square(diameter / 2, thickness)

    translate(diameter / 2, thickness) {
      rotate(borderAngle) {
        translate(0, -thickness) {
          square(
            borderHeight,
            thickness,
          );
        }
      }
    }

    translate(diameter / 2, thickness) {
      difference {
        circle(thickness)
        translate(-thickness, 0) {
          square(thickness * 2)
        }
      }
    }
  }

  fun OpenScad.main() {
    val diameter = 180
    val thickness = 4
    val borderHeight = 15
    val borderAngle = 45
    val logoThickness = 1

    use("../lurez-logo/lurez-logo.scad")

    difference {
      rotateExtrude {
        crossSection(
          diameter = diameter,
          thickness = thickness,
          borderHeight = borderHeight,
          borderAngle = borderAngle,
        )
      }

      translate(0, 0, thickness - logoThickness) {
        call(
          "lurez_logo",
          "width" to 140,
          "thickness" to logoThickness,
        )
      }
    }
  }
}

fun main() {
  with(PotPlate()) {
    writeOpenScad(System.out.asSink().buffered()) {
      main()
    }
  }
}
