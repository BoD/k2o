package org.jraf.k2o.stdlib

import org.jraf.k2o.test.renderOpenScad
import kotlin.test.Test
import kotlin.test.assertEquals

class RotateTest {
  @Test
  fun vectorRotationWrapsContent() {
    assertEquals(
      """
      rotate([90, 0, 45]) {
        cube(3);
      }
      """.trimIndent(),
      renderOpenScad {
        rotate(x = 90, z = 45) {
          Cube(3)
        }
      },
    )
  }

  @Test
  fun angleRotationWrapsContent() {
    assertEquals(
      """
      rotate(45) {
        square([3, 3]);
      }
      """.trimIndent(),
      renderOpenScad {
        rotate(angle = 45) {
          Square(3)
        }
      },
    )
  }
}

