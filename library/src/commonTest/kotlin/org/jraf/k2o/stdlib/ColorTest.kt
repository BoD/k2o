package org.jraf.k2o.stdlib

import org.jraf.k2o.test.renderOpenScad
import kotlin.test.Test
import kotlin.test.assertEquals

class ColorTest {
  @Test
  fun wrapsContent() {
    assertEquals(
      """
      color("red") {
        cube(2);
      }
      """.trimIndent(),
      renderOpenScad {
        color(Color.Red) {
          Cube(2)
        }
      },
    )
  }
}
