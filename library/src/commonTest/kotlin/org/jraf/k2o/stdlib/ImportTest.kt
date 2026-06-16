package org.jraf.k2o.stdlib

import org.jraf.k2o.test.renderOpenScad
import kotlin.test.Test
import kotlin.test.assertEquals

class ImportTest {
  @Test
  fun pathAndCenter() {
    assertEquals(
      """import("part.stl", center = true);""",
      renderOpenScad {
        Import("part.stl", center = true)
      },
    )
  }
}
