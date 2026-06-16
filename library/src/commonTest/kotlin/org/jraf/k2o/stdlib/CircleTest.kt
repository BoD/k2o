package org.jraf.k2o.stdlib

import org.jraf.k2o.test.renderOpenScad
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class CircleTest {
  @Test
  fun noArguments() {
    assertEquals(
      "circle();",
      renderOpenScad {
        Circle()
      },
    )
  }

  @Test
  fun radius() {
    assertEquals(
      "circle(2.5);",
      renderOpenScad {
        Circle(radius = 2.5)
      },
    )
  }

  @Test
  fun diameter() {
    assertEquals(
      "circle(d = 5);",
      renderOpenScad {
        Circle(diameter = 5)
      },
    )
  }

  @Test
  fun radiusAndDiameterFails() {
    assertFailsWith<IllegalStateException> {
      renderOpenScad {
        Circle(radius = 2, diameter = 4)
      }
    }
  }
}
