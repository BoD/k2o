package org.jraf.k2o.stdlib

import org.jraf.k2o.test.renderOpenScad
import kotlin.test.Test
import kotlin.test.assertEquals

class DifferenceTest {
  @Test
  fun wrapsContent() {
    assertEquals(
      """
      difference() {
        cube(4);
        sphere(r = 1);
      }
      """.trimIndent(),
      renderOpenScad {
        difference {
          Cube(4)
          Sphere(radius = 1)
        }
      },
    )
  }
}
