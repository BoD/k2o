package org.jraf.k2o.stdlib

import org.jraf.k2o.test.renderOpenScad
import kotlin.test.Test
import kotlin.test.assertEquals

class IntersectionTest {
  @Test
  fun twoChildren() {
    assertEquals(
      """
      intersection() {
        cube(2);
        sphere(r = 1);
      }
      """.trimIndent(),
      renderOpenScad {
        intersection {
          Cube(2)
          Sphere(radius = 1)
        }
      },
    )
  }
}
