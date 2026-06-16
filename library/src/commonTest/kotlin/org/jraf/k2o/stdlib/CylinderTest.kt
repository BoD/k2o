package org.jraf.k2o.stdlib

import org.jraf.k2o.test.renderOpenScad
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class CylinderTest {
  @Test
  fun heightOnly() {
    assertEquals(
      "cylinder(h = 10);",
      renderOpenScad {
        Cylinder(height = 10)
      },
    )
  }

  @Test
  fun radiusAndSegments() {
    assertEquals(
      $$"cylinder(h = 10, r = 2.5, $fn = 48);",
      renderOpenScad {
        Cylinder(height = 10, radius = 2.5, segments = 48)
      },
    )
  }

  @Test
  fun diameter() {
    assertEquals(
      "cylinder(h = 10, d = 5);",
      renderOpenScad {
        Cylinder(height = 10, diameter = 5)
      },
    )
  }

  @Test
  fun radiusAndDiameterFails() {
    assertFailsWith<IllegalStateException> {
      renderOpenScad {
        Cylinder(height = 10, radius = 2, diameter = 4)
      }
    }
  }
}
