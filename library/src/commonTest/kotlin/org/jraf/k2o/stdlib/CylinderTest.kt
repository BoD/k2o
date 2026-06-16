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
  fun radiusCentered() {
    assertEquals(
      "cylinder(h = 10, r = 2.5, center = true);",
      renderOpenScad {
        Cylinder(height = 10, radius = 2.5, center = true)
      },
    )
  }

  @Test
  fun cone() {
    assertEquals(
      "cylinder(h = 10, r1 = 5, r2 = 2);",
      renderOpenScad {
        Cylinder(height = 10, radius = 5, topRadius = 2)
      },
    )
  }

  @Test
  fun coneByDiameter() {
    assertEquals(
      "cylinder(h = 10, d1 = 10, d2 = 4);",
      renderOpenScad {
        Cylinder(height = 10, diameter = 10, topDiameter = 4)
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

  @Test
  fun mixRadiusAndDiameterConeFails() {
    assertFailsWith<IllegalStateException> {
      renderOpenScad {
        Cylinder(height = 10, radius = 2, topDiameter = 4)
      }
    }
  }

  @Test
  fun topRadiusWithoutRadiusFails() {
    assertFailsWith<IllegalStateException> {
      renderOpenScad {
        Cylinder(height = 10, topRadius = 4)
      }
    }
  }
}
