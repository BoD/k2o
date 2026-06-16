package org.jraf.k2o.stdlib

import org.jraf.k2o.test.renderOpenScad
import kotlin.test.Test
import kotlin.test.assertEquals

class MirrorTest {
  @Test
  fun wrapsContent() {
    assertEquals(
      """
      mirror([1, 0, 0]) {
        cube(2);
      }
      """.trimIndent(),
      renderOpenScad {
        mirror(x = 1) {
          Cube(2)
        }
      },
    )
  }
}
