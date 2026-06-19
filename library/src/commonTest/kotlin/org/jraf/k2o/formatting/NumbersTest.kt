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

package org.jraf.k2o.formatting

import kotlin.test.Test
import kotlin.test.assertEquals

class NumbersTest {
  @Test
  fun integralNumbersKeepIntegerFormat() {
    assertEquals("12", 12.formatted())
    assertEquals("12", 12L.formatted())
    assertEquals("12", 12.0.formatted())
    assertEquals("12", 12f.formatted())
  }

  @Test
  fun decimalNumbersAreRoundedToThreePlacesAndTrimmed() {
    assertEquals("1.235", 1.2346.formatted())
    assertEquals("1.2", 1.2.formatted())
    assertEquals("1.25", 1.25f.formatted())
    assertEquals("1.005", 1.005.formatted())
    assertEquals("0.005", 0.0049.formatted())
  }

  @Test
  fun negativeDecimalNumbersAreRoundedToThreePlacesAndTrimmed() {
    assertEquals("-1.235", (-1.2346).formatted())
    assertEquals("-1.2", (-1.2).formatted())
    assertEquals("0", (-0.0004).formatted())
  }
}
