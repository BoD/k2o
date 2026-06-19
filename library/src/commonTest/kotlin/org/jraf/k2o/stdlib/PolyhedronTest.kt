/*
 * This source is part of the
 *      _____  ___   ____
 *  __ / / _ \/ _ | / __/___  _______ _
 * / // / , _/ __ |/ _/_/ _ \/ __/ _ `/
 * \___/_/|_/_/ |_/_/ (_)___/_/  \_, /
 *                              /___/
 * repository.
 *
 * Copyright (C) 2026-present Benoit 'BoD' Lubek (BoD@JRAF.org)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.jraf.k2o.stdlib

import org.jraf.k2o.dsl.renderOpenScad
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
