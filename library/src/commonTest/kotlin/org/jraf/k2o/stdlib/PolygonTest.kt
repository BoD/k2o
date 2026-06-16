package org.jraf.k2o.stdlib

import org.jraf.k2o.test.renderOpenScad
import kotlin.test.Test
import kotlin.test.assertEquals

class PolygonTest {
  @Test
  fun points() {
    assertEquals(
      """
      polygon([
        [0, 0],
        [1.5, 0],
        [0, 2],
      ]);
      """.trimIndent(),
      renderOpenScad {
        Polygon(0 to 0, 1.5 to 0, 0 to 2)
      },
    )
  }
}
