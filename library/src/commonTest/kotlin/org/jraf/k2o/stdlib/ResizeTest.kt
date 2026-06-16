package org.jraf.k2o.stdlib

import org.jraf.k2o.test.renderOpenScad
import kotlin.test.Test
import kotlin.test.assertEquals

class ResizeTest {
  @Test
  fun wrapsContent() {
    assertEquals(
      """
      resize([10, 20, 0], auto = true) {
        cube(2);
      }
      """.trimIndent(),
      renderOpenScad {
        resize(x = 10, y = 20, auto = true) {
          Cube(2)
        }
      },
    )
  }
}

