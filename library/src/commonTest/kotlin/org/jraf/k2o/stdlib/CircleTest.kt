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
  fun radiusAndSegments() {
    assertEquals(
      $$"circle(2.5, $fn = 48);",
      renderOpenScad {
        Circle(radius = 2.5, segments = 48)
      },
    )
  }

  @Test
  fun segmentsOnly() {
    assertEquals(
      $$"circle($fn = 100);",
      renderOpenScad {
        Circle(segments = 100)
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
