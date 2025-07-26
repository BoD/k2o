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
import org.jraf.k2o.stdlib.call
import org.jraf.k2o.stdlib.color
import org.jraf.k2o.stdlib.cylinder
import org.jraf.k2o.stdlib.difference
import org.jraf.k2o.stdlib.hull
import org.jraf.k2o.stdlib.translate
import org.jraf.k2o.stdlib.use

class RedGreenSwitch {
  private fun OpenScad.supportBase(
    tokenDiameter: Double,
    paddingAroundToken: Double,
    thickness: Double,
    colorIndent: Double,
    pegHoleDiameter: Double,
    pegThicknessRatio: Double,
    magnetWidth: Double,
    magnetHeight: Double,
  ) {
    color("white") {
      difference {
        translate((tokenDiameter + paddingAroundToken * 2) / 2, (tokenDiameter + paddingAroundToken * 2) / 2, 0) {
          difference {
            hull {
              cylinder(diameter = tokenDiameter + paddingAroundToken * 2, height = thickness)
              translate(tokenDiameter + paddingAroundToken, 0, 0) {
                cylinder(diameter = tokenDiameter + paddingAroundToken * 2, height = thickness)
              }
            }

            // Left indent
            translate(0, 0, thickness - colorIndent) {
              cylinder(diameter = tokenDiameter, height = thickness)
            }

            // Right indent
            translate(tokenDiameter + paddingAroundToken, 0, thickness - colorIndent) {
              cylinder(diameter = tokenDiameter, height = thickness)
            }

            val magnetHeightAdjusted = magnetHeight * 1.1 // Make the magnet holes slightly taller than the magnets
            // Left magnet hole
            translate(0, 0, thickness - colorIndent - magnetHeightAdjusted) {
              call(
                "flexible_cylinder",
                "d" to magnetWidth,
                "h" to magnetHeightAdjusted,
                "flex" to 2,
              )
            }

            // Right magnet hole
            translate(tokenDiameter + paddingAroundToken, 0, thickness - colorIndent - magnetHeightAdjusted) {
              call(
                "flexible_cylinder",
                "d" to magnetWidth,
                "h" to magnetHeightAdjusted,
                "flex" to 2,
              )
            }
          }
        }

        val pegHeight = thickness * pegThicknessRatio * 1.1 // Make the peg holes slightly taller than the pegs
        // Left peg hole
        translate(paddingAroundToken / 2, (tokenDiameter + paddingAroundToken * 2) / 2, thickness - pegHeight) {
          cylinder(diameter = pegHoleDiameter, height = pegHeight)
        }

        // Right peg hole
        translate(
          tokenDiameter * 2 + paddingAroundToken * 2 + paddingAroundToken / 2,
          (tokenDiameter + paddingAroundToken * 2) / 2,
          thickness - pegHeight,
        ) {
          cylinder(diameter = pegHoleDiameter, height = pegHeight)
        }

        // Bottom peg hole
        translate(
          tokenDiameter + paddingAroundToken * 2 - paddingAroundToken / 2,
          paddingAroundToken / 2,
          thickness - pegHeight,
        ) {
          cylinder(diameter = pegHoleDiameter, height = pegHeight)
        }

        // Top peg hole
        translate(
          tokenDiameter + paddingAroundToken * 2 - paddingAroundToken / 2,
          tokenDiameter + paddingAroundToken * 2 - paddingAroundToken / 2,
          thickness - pegHeight,
        ) {
          cylinder(diameter = pegHoleDiameter, height = pegHeight)
        }
      }
    }
  }

  private fun OpenScad.colorIndicator(
    tokenDiameter: Double,
    thickness: Double,
    color: String,
  ) {
    color(color) {
      translate(tokenDiameter / 2, tokenDiameter / 2, 0) {
        cylinder(diameter = tokenDiameter, height = thickness)
      }
    }
  }

  private fun OpenScad.supportTop(
    tokenDiameter: Double,
    paddingAroundToken: Double,
    tokenThickness: Double,
    thickness: Double,
    pegDiameter: Double,
    pegThicknessRatio: Double,
    baseThickness: Double,
  ) {
    color("white") {
      val pegHeight = baseThickness * pegThicknessRatio
      val wiggleRoom = 1

      translate((tokenDiameter + paddingAroundToken * 2) / 2, (tokenDiameter + paddingAroundToken * 2) / 2, pegHeight) {
        difference {
          // Outer
          hull {
            cylinder(diameter = tokenDiameter + paddingAroundToken * 2, height = tokenThickness + thickness + wiggleRoom)
            translate(tokenDiameter + paddingAroundToken, 0, 0) {
              cylinder(diameter = tokenDiameter + paddingAroundToken * 2, height = tokenThickness + thickness + wiggleRoom)
            }
          }

          // Inner
          hull {
            cylinder(diameter = tokenDiameter, height = tokenThickness + wiggleRoom)
            translate(tokenDiameter + paddingAroundToken, 0, 0) {
              cylinder(diameter = tokenDiameter, height = tokenThickness + wiggleRoom)
            }
          }

          // Top opening
          hull {
            translate(0, 0, tokenThickness) {
              cylinder(diameter = tokenDiameter - paddingAroundToken * 2, height = thickness + wiggleRoom)
            }
            translate(tokenDiameter + paddingAroundToken, 0, tokenThickness) {
              cylinder(diameter = tokenDiameter - paddingAroundToken * 2, height = thickness + wiggleRoom)
            }
          }
        }
      }

      // Left peg hole
      translate(paddingAroundToken / 2, (tokenDiameter + paddingAroundToken * 2) / 2, 0) {
        cylinder(diameter = pegDiameter, height = pegHeight)
      }

      // Right peg hole
      translate(
        tokenDiameter * 2 + paddingAroundToken * 2 + paddingAroundToken / 2,
        (tokenDiameter + paddingAroundToken * 2) / 2,
        0,
      ) {
        cylinder(diameter = pegDiameter, height = pegHeight)
      }

      // Bottom peg hole
      translate(
        tokenDiameter + paddingAroundToken * 2 - paddingAroundToken / 2,
        paddingAroundToken / 2,
        0,
      ) {
        cylinder(diameter = pegDiameter, height = pegHeight)
      }

      // Top peg hole
      translate(
        tokenDiameter + paddingAroundToken * 2 - paddingAroundToken / 2,
        tokenDiameter + paddingAroundToken * 2 - paddingAroundToken / 2,
        0,
      ) {
        cylinder(diameter = pegDiameter, height = pegHeight)
      }
    }
  }

  private fun OpenScad.token(
    diameter: Double,
    thickness: Double,
    magnetWidth: Double,
    magnetHeight: Double,
    grabDiameter: Double,
    grabIndent: Double,
  ) {
    color("white") {
      translate(diameter / 2, diameter / 2, 0) {
        difference {
          cylinder(diameter = diameter, height = thickness)

          val magnetHeightAdjusted = magnetHeight * 1.1 // Make the magnet hole slightly taller than the magnet
          // Magnet hole
          call(
            "flexible_cylinder",
            "d" to magnetWidth,
            "h" to magnetHeightAdjusted,
            "flex" to 2,
          )

          // Grab indent
          translate(0, 0, thickness - grabIndent) {
            cylinder(diameter = grabDiameter, height = grabIndent)
          }
        }
      }
    }
  }

  private fun OpenScad.everythingAssembled(
    magnetWidth: Double,
    magnetHeight: Double,
    tokenDiameter: Double,
    tokenGrabDiameter: Double,
    tokenGrabIndent: Double,
    tokenThickness: Double,
    paddingAroundToken: Double,
    supportBaseThickness: Double,
    supportTopThickness: Double,
    colorIndent: Double,
    pegDiameter: Double,
    pegHoleDiameter: Double,
    pegThicknessRatio: Double,
  ) {
    // Support base
    supportBase(
      tokenDiameter = tokenDiameter,
      paddingAroundToken = paddingAroundToken,
      thickness = supportBaseThickness,
      colorIndent = colorIndent,
      pegHoleDiameter = pegHoleDiameter,
      pegThicknessRatio = pegThicknessRatio,
      magnetWidth = magnetWidth,
      magnetHeight = magnetHeight,
    )

    // Red color indicator
    translate(paddingAroundToken, paddingAroundToken, supportBaseThickness - colorIndent) {
      colorIndicator(
        tokenDiameter = tokenDiameter,
        thickness = colorIndent,
        color = "red",
      )
    }

    // Green color indicator
    translate(tokenDiameter + paddingAroundToken * 2, paddingAroundToken, supportBaseThickness - colorIndent) {
      colorIndicator(
        tokenDiameter = tokenDiameter,
        thickness = colorIndent,
        color = "green",
      )
    }

    val pegHeight = supportBaseThickness * pegThicknessRatio

    // Support top
    translate(0, 0, supportBaseThickness - pegHeight) {
      supportTop(
        tokenDiameter = tokenDiameter,
        paddingAroundToken = paddingAroundToken,
        tokenThickness = tokenThickness,
        thickness = supportTopThickness,
        pegDiameter = pegDiameter,
        pegThicknessRatio = pegThicknessRatio,
        baseThickness = supportBaseThickness,
      )
    }

    // Token
    translate(paddingAroundToken + tokenDiameter, paddingAroundToken, supportBaseThickness) {
      token(
        diameter = tokenDiameter,
        thickness = tokenThickness,
        magnetWidth = magnetWidth,
        magnetHeight = magnetHeight,
        grabDiameter = tokenGrabDiameter,
        grabIndent = tokenGrabIndent,
      )
    }
  }

  private fun OpenScad.everythingExploded(
    magnetWidth: Double,
    magnetHeight: Double,
    tokenDiameter: Double,
    tokenGrabDiameter: Double,
    tokenGrabIndent: Double,
    tokenThickness: Double,
    paddingAroundToken: Double,
    supportBaseThickness: Double,
    supportTopThickness: Double,
    colorIndent: Double,
    pegDiameter: Double,
    pegHoleDiameter: Double,
    pegThicknessRatio: Double,
  ) {
    // Support base
    supportBase(
      tokenDiameter = tokenDiameter,
      paddingAroundToken = paddingAroundToken,
      thickness = supportBaseThickness,
      colorIndent = colorIndent,
      pegHoleDiameter = pegHoleDiameter,
      pegThicknessRatio = pegThicknessRatio,
      magnetWidth = magnetWidth,
      magnetHeight = magnetHeight,
    )

    // Red color indicator
    translate(50, 0, 0) {
      colorIndicator(
        tokenDiameter = tokenDiameter,
        thickness = colorIndent,
        color = "red",
      )
    }

    // Green color indicator
    translate(75, 0, 0) {
      colorIndicator(
        tokenDiameter = tokenDiameter,
        thickness = colorIndent,
        color = "green",
      )
    }

    val pegHeight = supportBaseThickness * pegThicknessRatio

    // Support top
    translate(0, 30, 0) {
      supportTop(
        tokenDiameter = tokenDiameter,
        paddingAroundToken = paddingAroundToken,
        tokenThickness = tokenThickness,
        thickness = supportTopThickness,
        pegDiameter = pegDiameter,
        pegThicknessRatio = pegThicknessRatio,
        baseThickness = supportBaseThickness,
      )
    }

    // Token
    translate(100, 0, 0) {
      token(
        diameter = tokenDiameter,
        thickness = tokenThickness,
        magnetWidth = magnetWidth,
        magnetHeight = magnetHeight,
        grabDiameter = tokenGrabDiameter,
        grabIndent = tokenGrabIndent,
      )
    }
  }

  fun OpenScad.main() {
    use("flexible_cylinder.scad")
    val magnetWidth = 9.9
    val magnetHeight = 6.0
    val paddingAroundToken = 3.2
    val tokenDiameter = 20.0
    val tokenGrabDiameter = tokenDiameter - paddingAroundToken * 2
    val tokenGrabIndent = 1.0
    val tokenThickness = magnetHeight + 1 + tokenGrabIndent
    val supportBaseThickness = magnetHeight + 1.5
    val supportTopThickness = 1.0
    val colorIndent = 0.8
    val pegDiameter = 2.0
    val pegHoleDiameter = 2.2
    val pegThicknessRatio = 0.5
    everythingExploded(
      magnetWidth = magnetWidth,
      magnetHeight = magnetHeight,
      tokenDiameter = tokenDiameter,
      tokenGrabDiameter = tokenGrabDiameter,
      tokenGrabIndent = tokenGrabIndent,
      tokenThickness = tokenThickness,
      paddingAroundToken = paddingAroundToken,
      supportBaseThickness = supportBaseThickness,
      supportTopThickness = supportTopThickness,
      colorIndent = colorIndent,
      pegDiameter = pegDiameter,
      pegHoleDiameter = pegHoleDiameter,
      pegThicknessRatio = pegThicknessRatio,
    )
  }
}

fun main() {
  with(RedGreenSwitch()) {
    writeOpenScad(SystemFileSystem.sink(Path("/Users/bod/gitrepo/openscad-projects/red-green-switch/tmp.scad")).buffered()) {
      main()
    }
  }
}
