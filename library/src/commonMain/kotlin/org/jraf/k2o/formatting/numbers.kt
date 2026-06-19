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

import kotlin.math.floor

/**
 * Formats this number for inclusion in OpenSCAD output.
 *
 * Integers are printed as-is, and whole floating-point values are printed without a decimal part (e.g. `2.0` becomes
 * `"2"`). Fractional values are rounded to three decimal places (micrometre precision at OpenSCAD's millimetre scale)
 * with trailing zeros trimmed, keeping the generated code compact and readable.
 */
fun Number.formatted(): String {
  return if (this is Int || this is Long) {
    this.toString()
  } else if (this is Double && this % 1.0 == 0.0) {
    this.toLong().toString()
  } else if (this is Float && this % 1f == 0f) {
    this.toLong().toString()
  } else if (this is Double) {
    this.formattedDecimal()
  } else if (this is Float) {
    this.toDouble().formattedDecimal()
  } else {
    // Fallback for other types, e.g. Short, Byte, etc.
    this.toString()
  }
}

private fun Double.formattedDecimal(): String {
  if (!isFinite()) return toString()

  val sign = if (this < 0) "-" else ""
  val rounded = floor(abs() * 1000 + 0.5).toLong()
  if (rounded == 0L) return "0"

  val integerPart = rounded / 1000
  val decimalPart = rounded % 1000
  if (decimalPart == 0L) return "$sign$integerPart"

  return "$sign$integerPart.${decimalPart.toString().padStart(3, '0').trimEnd('0')}"
}

private fun Double.abs(): Double {
  return if (this < 0) {
    -this
  } else {
    this
  }
}
