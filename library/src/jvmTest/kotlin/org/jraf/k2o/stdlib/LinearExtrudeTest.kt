package org.jraf.k2o.stdlib

import org.jraf.k2o.test.renderOpenScad
import kotlin.test.Test
import kotlin.test.assertEquals

class LinearExtrudeTest {
  @Test
  fun heightOnly() {
    assertEquals(
      """
      linear_extrude(5) {
        square([2, 2]);
      }
      """.trimIndent(),
      renderOpenScad {
        linearExtrude(5) {
          Square(2)
        }
      },
    )
  }

  @Test
  fun optionalArguments() {
    assertEquals(
      """
      linear_extrude(
        height = 5,
        v = [0, 0, 1],
        center = true,
        twist = 90,
        scale = 1.5,
        slices = 4,
        segments = 16,
        convexity = 2
      ) {
        square([2, 2]);
      }
      """.trimIndent(),
      renderOpenScad {
        linearExtrude(
          height = 5,
          v = [0, 0, 1],
          center = true,
          twist = 90,
          scale = 1.5,
          slices = 4,
          segments = 16,
          convexity = 2,
        ) {
          Square(2)
        }
      },
    )
  }
}
