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

import org.jraf.k2o.formatting.formatted

data class Vect(
  val x: Number,
  val y: Number? = null,
  val z: Number? = null,
) {
  override fun toString(): String {
    return buildString {
      append("[")
      append(x.formatted())
      if (y == null) {
        if (z == null) {
          append("]")
        } else {
          append(", ")
          append(", 0")
          append(z.formatted())
          append("]")
        }
      } else {
        append(", ")
        append(y.formatted())
        if (z == null) {
          append("]")
        } else {
          append(", ")
          append(z.formatted())
          append("]")
        }
      }
    }
  }

  companion object {
    operator fun of(vararg elements: Number): Vect {
      check(elements.isNotEmpty()) { "At least one element is required" }
      check(elements.size <= 3) { "At most three elements are allowed" }
      return Vect(
        x = elements[0],
        y = elements.getOrNull(1),
        z = elements.getOrNull(2),
      )
    }
  }
}
