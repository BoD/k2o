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
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
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
