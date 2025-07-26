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

package org.jraf.k2o.stdlib

import org.jraf.k2o.dsl.OpenScad
import org.jraf.k2o.dsl.addLine
import org.jraf.k2o.formatting.formatted

fun OpenScad.cylinder(
  height: Number,
  radius: Number? = null,
  diameter: Number? = null,
  segments: Int? = null,
) {
  val segments = if (segments != null) ", \$fn = $segments" else ""
  addLine(
    if (radius == null && diameter == null) {
      "cylinder(h = ${height.formatted()}$segments);"
    } else if (radius != null && diameter != null) {
      error("Only one of radius or diameter can be specified")
    } else if (radius != null) {
      "cylinder(h = ${height.formatted()}, r = ${radius.formatted()}$segments);"
    } else {
      "cylinder(h = ${height.formatted()}, d = ${diameter!!.formatted()}$segments);"
    },
  )
}
