package org.jraf.k2o.stdlib

import org.jraf.k2o.test.renderOpenScad
import kotlin.test.Test
import kotlin.test.assertEquals

class SquareTest {
  @Test
  fun defaultHeightMatchesWidth() {
    assertEquals(
      "square([4, 4]);",
      renderOpenScad {
        Square(width = 4)
      },
    )
  }

  @Test
  fun explicitDimensions() {
    assertEquals(
      "square([4, 2.5]);",
      renderOpenScad {
        Square(width = 4, height = 2.5)
      },
    )
  }
}

