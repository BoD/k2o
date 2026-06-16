package org.jraf.k2o.stdlib

import org.jraf.k2o.test.renderOpenScad
import kotlin.test.Test
import kotlin.test.assertEquals

class UnionTest {
  @Test
  fun wrapsContent() {
    assertEquals(
      """
      union() {
        cube(2);
        sphere(r = 1);
      }
      """.trimIndent(),
      renderOpenScad {
        union {
          Cube(2)
          Sphere(radius = 1)
        }
      },
    )
  }
}

