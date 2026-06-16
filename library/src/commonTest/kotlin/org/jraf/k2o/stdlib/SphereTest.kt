package org.jraf.k2o.stdlib

import org.jraf.k2o.test.renderOpenScad
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class SphereTest {
  @Test
  fun noArguments() {
    assertEquals(
      "sphere();",
      renderOpenScad {
        Sphere()
      },
    )
  }

  @Test
  fun radius() {
    assertEquals(
      "sphere(r = 2.5);",
      renderOpenScad {
        Sphere(radius = 2.5)
      },
    )
  }

  @Test
  fun diameter() {
    assertEquals(
      "sphere(d = 5);",
      renderOpenScad {
        Sphere(diameter = 5)
      },
    )
  }

  @Test
  fun radiusAndSegments() {
    assertEquals(
      $$"sphere(r = 2.5, $fn = 64);",
      renderOpenScad {
        Sphere(radius = 2.5, segments = 64)
      },
    )
  }

  @Test
  fun segmentsOnly() {
    assertEquals(
      $$"sphere($fn = 64);",
      renderOpenScad {
        Sphere(segments = 64)
      },
    )
  }

  @Test
  fun radiusAndDiameterFails() {
    assertFailsWith<IllegalStateException> {
      renderOpenScad {
        Sphere(radius = 2, diameter = 4)
      }
    }
  }
}

