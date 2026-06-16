package org.jraf.k2o.stdlib

import org.jraf.k2o.test.renderOpenScad
import kotlin.test.Test
import kotlin.test.assertEquals

class ScaleTest {
  @Test
  fun singleAxis() {
    assertEquals(
      """
      scale([2, 1, 1]) {
        cube(1);
      }
      """.trimIndent(),
      renderOpenScad {
        scale(x = 2) {
          Cube(1)
        }
      },
    )
  }

  @Test
  fun allAxes() {
    assertEquals(
      """
      scale([2, 3, 0.5]) {
        cube(1);
      }
      """.trimIndent(),
      renderOpenScad {
        scale(x = 2, y = 3, z = 0.5) {
          Cube(1)
        }
      },
    )
  }
}
