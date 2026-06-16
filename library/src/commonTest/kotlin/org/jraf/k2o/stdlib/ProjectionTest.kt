package org.jraf.k2o.stdlib

import org.jraf.k2o.test.renderOpenScad
import kotlin.test.Test
import kotlin.test.assertEquals

class ProjectionTest {
  @Test
  fun shadow() {
    assertEquals(
      """
      projection() {
        cube(2);
      }
      """.trimIndent(),
      renderOpenScad {
        projection {
          Cube(2)
        }
      },
    )
  }

  @Test
  fun cutWithConvexity() {
    assertEquals(
      """
      projection(cut = true, convexity = 2) {
        cube(2);
      }
      """.trimIndent(),
      renderOpenScad {
        projection(cut = true, convexity = 2) {
          Cube(2)
        }
      },
    )
  }
}
