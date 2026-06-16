package org.jraf.k2o.stdlib

import org.jraf.k2o.test.renderOpenScad
import kotlin.test.Test
import kotlin.test.assertEquals

class RotateExtrudeTest {
  @Test
  fun defaultDegrees() {
    assertEquals(
      """
      rotate_extrude(360) {
        square([2, 2]);
      }
      """.trimIndent(),
      renderOpenScad {
        rotateExtrude {
          Square(2)
        }
      },
    )
  }

  @Test
  fun customDegrees() {
    assertEquals(
      """
      rotate_extrude(90) {
        square([2, 2]);
      }
      """.trimIndent(),
      renderOpenScad {
        rotateExtrude(90) {
          Square(2)
        }
      },
    )
  }
}

