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

class PolygonTest {
  @Test
  fun points() {
    assertEquals(
      """
      polygon([
        [0, 0],
        [1.5, 0],
        [0, 2],
      ]);
      """.trimIndent(),
      renderOpenScad {
        Polygon(0 to 0, 1.5 to 0, 0 to 2)
      },
    )
  }

  @Test
  fun pathsAndConvexity() {
    assertEquals(
      """
      polygon([
        [0, 0],
        [4, 0],
        [4, 4],
        [0, 4],
      ], paths = [[0, 1, 2, 3]], convexity = 2);
      """.trimIndent(),
      renderOpenScad {
        Polygon(
          0 to 0, 4 to 0, 4 to 4, 0 to 4,
          paths = listOf(listOf(0, 1, 2, 3)),
          convexity = 2,
        )
      },
    )
  }
}
