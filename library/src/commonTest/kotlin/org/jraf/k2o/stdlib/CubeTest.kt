package org.jraf.k2o.stdlib

import org.jraf.k2o.test.renderOpenScad
import kotlin.test.Test
import kotlin.test.assertEquals

class CubeTest {
  @Test
  fun uniformSize() {
    assertEquals(
      "cube(3);",
      renderOpenScad {
        Cube(3)
      },
    )
  }

  @Test
  fun uniformSizeCentered() {
    assertEquals(
      "cube(3, center = true);",
      renderOpenScad {
        Cube(3, center = true)
      },
    )
  }

  @Test
  fun dimensions() {
    assertEquals(
      "cube([1, 2.5, 3]);",
      renderOpenScad {
        Cube(1, 2.5, 3)
      },
    )
  }

  @Test
  fun dimensionsCentered() {
    assertEquals(
      "cube([1, 2.5, 3], center = true);",
      renderOpenScad {
        Cube(1, 2.5, 3, center = true)
      },
    )
  }
}

