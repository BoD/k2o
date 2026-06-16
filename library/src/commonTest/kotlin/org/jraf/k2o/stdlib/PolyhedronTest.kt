package org.jraf.k2o.stdlib

import org.jraf.k2o.test.renderOpenScad
import kotlin.test.Test
import kotlin.test.assertEquals

class PolyhedronTest {
  @Test
  fun pointsAndFaces() {
    assertEquals(
      """
      polyhedron(
        points = [
          [0, 0, 0],
          [1, 0, 0],
          [0, 1, 0],
          [0, 0, 1],
        ],
        faces = [
          [0, 1, 2],
          [0, 1, 3],
          [1, 2, 3],
          [0, 2, 3],
        ]
      );
      """.trimIndent(),
      renderOpenScad {
        Polyhedron(
          points = listOf(
            Triple(0, 0, 0),
            Triple(1, 0, 0),
            Triple(0, 1, 0),
            Triple(0, 0, 1),
          ),
          faces = listOf(
            listOf(0, 1, 2),
            listOf(0, 1, 3),
            listOf(1, 2, 3),
            listOf(0, 2, 3),
          ),
        )
      },
    )
  }

  @Test
  fun convexity() {
    assertEquals(
      """
      polyhedron(
        points = [
          [0, 0, 0],
        ],
        faces = [
          [0],
        ],
        convexity = 10
      );
      """.trimIndent(),
      renderOpenScad {
        Polyhedron(
          points = listOf(Triple(0, 0, 0)),
          faces = listOf(listOf(0)),
          convexity = 10,
        )
      },
    )
  }
}
