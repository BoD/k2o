package org.jraf.k2o.stdlib

import org.jraf.k2o.test.renderOpenScad
import kotlin.test.Test
import kotlin.test.assertEquals

class CallTest {
  @Test
  fun withoutContent() {
    assertEquals(
      """
      star(
        p = 5,
        label = "outer",
        mirrored = true,
      );
      """.trimIndent(),
      renderOpenScad {
        Call("star", "p" to 5, "label" to "outer", "mirrored" to true)
      },
    )
  }

  @Test
  fun withContent() {
    assertEquals(
      """
      shell(
        thickness = 2,
      ) {
        cube(4);
      }
      """.trimIndent(),
      renderOpenScad {
        Call("shell", "thickness" to 2) {
          Cube(4)
        }
      },
    )
  }
}
