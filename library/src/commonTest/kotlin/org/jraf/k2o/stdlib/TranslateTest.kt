package org.jraf.k2o.stdlib

import org.jraf.k2o.test.renderOpenScad
import kotlin.test.Test
import kotlin.test.assertEquals

class TranslateTest {
  @Test
  fun wrapsContent() {
    assertEquals(
      """
      translate([1, 2.5, 0]) {
        cube(3);
      }
      """.trimIndent(),
      renderOpenScad {
        translate(x = 1, y = 2.5) {
          Cube(3)
        }
      },
    )
  }
}

