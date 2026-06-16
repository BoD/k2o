package org.jraf.k2o.stdlib

import org.jraf.k2o.test.renderOpenScad
import kotlin.test.Test
import kotlin.test.assertEquals

class UseTest {
  @Test
  fun path() {
    assertEquals(
      "use <star.scad>;",
      renderOpenScad {
        Use("star.scad")
      },
    )
  }
}

