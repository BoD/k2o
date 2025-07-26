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

import kotlinx.io.buffered
import kotlinx.io.files.Path
import kotlinx.io.files.SystemFileSystem
import org.jraf.k2o.dsl.OpenScad
import org.jraf.k2o.dsl.writeOpenScad
import org.jraf.k2o.math.atan
import org.jraf.k2o.math.cos
import org.jraf.k2o.math.sin
import org.jraf.k2o.stdlib.call
import org.jraf.k2o.stdlib.difference
import org.jraf.k2o.stdlib.hull
import org.jraf.k2o.stdlib.import
import org.jraf.k2o.stdlib.resize
import org.jraf.k2o.stdlib.rotate
import org.jraf.k2o.stdlib.sphere
import org.jraf.k2o.stdlib.translate
import org.jraf.k2o.stdlib.use
import kotlin.math.sqrt

class FreeboxCoverCurved {
  private fun OpenScad.frontPlateLine(
    thickness: Int,
    frontHeight: Int,
    frontBottomWidth: Int,
    frontTopWidth: Int,
    topBackWidth: Int,
    topDepth: Int,
    curveRadius: Int,
    curveAngle: Double,
    step: Int,
    stepWidth: Double,
    i: Int,
  ) {
    val roundBevelFraction = 0.2
    val cornerRadius = frontBottomWidth * roundBevelFraction
    val frontTopBottomRatio = frontTopWidth.toDouble() / frontBottomWidth

    // Bottom
    val angleBottom = (curveAngle / step) * i - curveAngle / 2
    val z = if (i * stepWidth < cornerRadius) {
      cornerRadius - sqrt(cornerRadius * cornerRadius - (cornerRadius - i * stepWidth) * (cornerRadius - i * stepWidth))
    } else if (i * stepWidth > frontBottomWidth - cornerRadius) {
      cornerRadius - sqrt(cornerRadius * cornerRadius - (cornerRadius - (step - i) * stepWidth) * (cornerRadius - (step - i) * stepWidth))
    } else {
      0.0
    }

    translate(curveRadius * sin(angleBottom), curveRadius * cos(angleBottom), z + thickness / 2) {
      sphere(thickness / 2)
    }

    // Top
    val angleTop = (curveAngle * frontTopBottomRatio / step) * i - curveAngle * frontTopBottomRatio / 2
    translate(curveRadius * sin(angleTop), curveRadius * cos(angleTop), frontHeight - thickness / 2) {
      sphere(thickness / 2)
    }
  }

  private fun OpenScad.frontPlate(
    thickness: Int,
    frontHeight: Int,
    frontBottomWidth: Int,
    frontTopWidth: Int,
    topBackWidth: Int,
    topDepth: Int,
    curveRadius: Int,
    curveAngle: Double,
    step: Int,
  ) {
    val stepWidth = frontBottomWidth.toDouble() / step
    translate(0, curveRadius + thickness / 2, 0) {
      rotate(0, 0, 180) {
        for (i in 0 until step) {
          hull {
            frontPlateLine(
              thickness = thickness,
              frontHeight = frontHeight,
              frontBottomWidth = frontBottomWidth,
              frontTopWidth = frontTopWidth,
              topBackWidth = topBackWidth,
              topDepth = topDepth,
              curveRadius = curveRadius,
              curveAngle = curveAngle,
              step = step,
              stepWidth = stepWidth,
              i = i,
            )
            frontPlateLine(
              thickness = thickness,
              frontHeight = frontHeight,
              frontBottomWidth = frontBottomWidth,
              frontTopWidth = frontTopWidth,
              topBackWidth = topBackWidth,
              topDepth = topDepth,
              curveRadius = curveRadius,
              curveAngle = curveAngle,
              step = step,
              stepWidth = stepWidth,
              i = i + 1,
            )
          }
        }
      }
    }
  }

  private fun OpenScad.logo(height: Int, thickness: Int, curveRadius: Int) {
    translate(0, curveRadius + thickness, height / 2) {
      rotate(0, 0, -90) {
        call("cylinder_extrude", "r_cyl" to curveRadius, "r_delta" to thickness * 2, "h" to height) {
          resize(0, height.toDouble(), 0, auto = true) {
            import("lurez-full.svg", center = true)
          }
        }
      }
    }
  }

  private fun OpenScad.front(
    thickness: Int,
    frontHeight: Int,
    frontBottomWidth: Int,
    frontTopWidth: Int,
    logoHeight: Int,
    logoThickness: Int,
    topBackWidth: Int,
    topDepth: Int,
    curveRadius: Int,
    curveAngle: Double,
    step: Int,
  ) {
    difference {
      frontPlate(
        thickness = thickness,
        frontHeight = frontHeight,
        frontBottomWidth = frontBottomWidth,
        frontTopWidth = frontTopWidth,
        topBackWidth = topBackWidth,
        topDepth = topDepth,
        curveRadius = curveRadius,
        curveAngle = curveAngle,
        step = step,
      )
      translate(0, 0, (frontHeight - logoHeight) / 2) {
        logo(height = logoHeight, thickness = logoThickness, curveRadius = curveRadius)
      }
    }
  }

  private fun OpenScad.top(
    thickness: Int,
    frontHeight: Int,
    frontBottomWidth: Int,
    frontTopWidth: Int,
    topBackWidth: Int,
    topDepth: Int,
    curveRadius: Int,
    curveAngle: Double,
    step: Int,
  ) {
    val frontTopBottomRatio = frontTopWidth.toDouble() / frontBottomWidth
    val stepWidth = frontBottomWidth.toDouble() / step
    hull {
      translate(0, curveRadius + thickness / 2, 0) {
        rotate(0, 0, 180) {
          for (i in 0..step) {
            val angleTop = (curveAngle * frontTopBottomRatio / step) * i - curveAngle * frontTopBottomRatio / 2
            translate(curveRadius * sin(angleTop), curveRadius * cos(angleTop), frontHeight - thickness / 2) {
              sphere(thickness / 2)
            }
          }
        }
      }

      translate(-topBackWidth / 2, topDepth, frontHeight - thickness / 2) {
        sphere(thickness / 2)
      }

      translate(topBackWidth / 2, topDepth, frontHeight - thickness / 2) {
        sphere(thickness / 2)
      }
    }
  }

  fun OpenScad.freeboxCover() {
    val fbxHeight = 67

    val thickness = 5

    // A bit shorter than the fbx, so it won't touch the floor.
    val frontHeight = fbxHeight - 5
    val frontBottomWidth = 150
    val frontTopWidth = 170

    val topBackWidth = frontTopWidth - 10
    val topDepth = 40

    val logoHeight = 30
    val logoThickness = 1

    val curveRadius = 600
    val curveAngle =
      atan((frontBottomWidth / 2) / sqrt(curveRadius.toDouble() * curveRadius - (frontBottomWidth / 2) * (frontBottomWidth / 2))) * 2

    val step = 100

    front(
      thickness = thickness,
      frontHeight = frontHeight,
      frontBottomWidth = frontBottomWidth,
      frontTopWidth = frontTopWidth,
      topBackWidth = topBackWidth,
      topDepth = topDepth,
      logoHeight = logoHeight,
      logoThickness = logoThickness,
      curveRadius = curveRadius,
      curveAngle = curveAngle,
      step = step,
    )

    top(
      thickness = thickness,
      frontHeight = frontHeight,
      frontBottomWidth = frontBottomWidth,
      frontTopWidth = frontTopWidth,
      topBackWidth = topBackWidth,
      topDepth = topDepth,
      curveRadius = curveRadius,
      curveAngle = curveAngle,
      step = step,
    )
  }

  fun OpenScad.main() {
    use("cylinder_extrude.scad")
    freeboxCover()
  }
}

fun main() {
  with(FreeboxCoverCurved()) {
    writeOpenScad(SystemFileSystem.sink(Path("/Users/bod/gitrepo/openscad-projects/freebox-cover/tmp.scad")).buffered()) {
      main()
    }
  }
}
