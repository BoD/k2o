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

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class VectTest {
  @Test
  fun oneElement() {
    assertEquals("[1]", Vect.of(1).toString())
  }

  @Test
  fun twoElements() {
    assertEquals("[1, 2.5]", Vect.of(1, 2.5).toString())
  }

  @Test
  fun threeElements() {
    assertEquals("[1, 2.5, 3]", Vect.of(1, 2.5, 3).toString())
  }

  @Test
  fun zElementWithoutYUsesZeroForY() {
    assertEquals("[1, 0, 3]", Vect(x = 1, z = 3).toString())
  }

  @Test
  fun noElementsFails() {
    assertFailsWith<IllegalStateException> {
      Vect.of()
    }
  }

  @Test
  fun moreThanThreeElementsFails() {
    assertFailsWith<IllegalStateException> {
      Vect.of(1, 2, 3, 4)
    }
  }
}
