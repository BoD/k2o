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
import org.jraf.k2o.stdlib.cylinder
import org.jraf.k2o.stdlib.difference
import org.jraf.k2o.stdlib.linearExtrude
import org.jraf.k2o.stdlib.mirror
import org.jraf.k2o.stdlib.polygon
import org.jraf.k2o.stdlib.translate

class PlantHolder {
  private fun OpenScad.half(
    width: Int,
    height: Int,
  ) {
    polygon(
      width / 2 to (height - 10),
      0 to height,
      0 to 10,
      width / 2 to 0,
    )
  }

  fun OpenScad.main() {
    val width = 20
    val height = 60
    val thickness = 2

    difference {
      linearExtrude(thickness) {
        half(
          width = width,
          height = height,
        )
        mirror(1, 0, 0) {
          half(
            width = width,
            height = height,
          )
        }
      }

      translate(0, 21, 0) {
        cylinder(diameter = 15, height = thickness)
      }

      translate(0, 40, 0) {
        cylinder(diameter = 15, height = thickness)
      }
    }
  }
}

fun main() {
  with(PlantHolder()) {
    writeOpenScad(System.out.asSink().buffered()) {
      main()
    }
  }
}
