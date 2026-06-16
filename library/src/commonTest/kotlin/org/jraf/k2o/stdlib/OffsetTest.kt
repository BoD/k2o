package org.jraf.k2o.stdlib

import org.jraf.k2o.test.renderOpenScad
import kotlin.test.Test
import kotlin.test.assertEquals

class OffsetTest {
  @Test
  fun radius() {
    assertEquals(
      """
      offset(r = 1.5) {
        square([2, 2]);
      }
      """.trimIndent(),
      renderOpenScad {
        offset(radius = 1.5) {
          Square(2)
        }
      },
    )
  }

  @Test
  fun deltaWithChamfer() {
    assertEquals(
      """
      offset(delta = 1.5, chamfer = true) {
        square([2, 2]);
      }
      """.trimIndent(),
      renderOpenScad {
        offset(delta = 1.5, chamfer = true) {
          Square(2)
        }
      },
    )
  }
}
