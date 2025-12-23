/*
 * This source is part of the
 *      _____  ___   ____
 *  __ / / _ \/ _ | / __/___  _______ _
 * / // / , _/ __ |/ _/_/ _ \/ __/ _ `/
 * \___/_/|_/_/ |_/_/ (_)___/_/  \_, /
 *                              /___/
 * repository.
 *
 * Copyright (C) 2025-present Benoit 'BoD' Lubek (BoD@JRAF.org)
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

/**
 * Formats a number such as if the decimal is 0, it is not displayed.
 */
fun Number.formatted(): String {
  return if (this is Int || this is Long) {
    this.toString()
  } else if (this is Double && this % 1.0 == 0.0) {
    this.toInt().toString()
  } else if (this is Float && this % 1f == 0f) {
    this.toInt().toString()
  } else if (this is Double || this is Float) {
    String.format("%.3f", this).removeTrailingZeros()
  } else {
    // Fallback for other types, e.g. Short, Byte, etc.
    this.toString()
  }
}

private fun String.removeTrailingZeros(): String {
  var result = this
  while (result.endsWith("0")) {
    result = result.removeSuffix("0")
  }
  return result
}
