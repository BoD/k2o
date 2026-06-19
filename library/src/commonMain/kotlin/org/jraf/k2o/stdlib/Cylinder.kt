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

package org.jraf.k2o.stdlib

import androidx.compose.runtime.Composable
import org.jraf.k2o.dsl.Line
import org.jraf.k2o.formatting.formatted

/**
 * Creates a [cylinder](https://en.wikibooks.org/wiki/OpenSCAD_User_Manual/Primitive_Solids#cylinder) standing on the
 * XY plane.
 *
 * At most one of [radius] or [diameter] may be specified; specifying both throws. When neither is given, OpenSCAD's
 * default size is used.
 *
 * Sizing is done either with [radius] or with [diameter] (the two are mutually exclusive). When only [radius] (or
 * [diameter]) is given, the result is a regular cylinder. When [topRadius] (or [topDiameter]) is also given, the
 * result is a cone or truncated cone: [radius] is the radius at the bottom and [topRadius] the radius at the top.
 *
 * @param height The height of the cylinder along the Z axis.
 * @param radius The radius of the cylinder, or the radius of the bottom end when [topRadius] is also given.
 * @param topRadius The radius of the top end. When given, [radius] becomes the bottom radius and the result is a cone.
 * @param diameter The diameter of the cylinder, or the diameter of the bottom end when [topDiameter] is also given.
 * @param topDiameter The diameter of the top end. When given, [diameter] becomes the bottom diameter and the result
 * is a cone.
 * @param center When `false` (the default), the cylinder sits on the XY plane. When `true`, it is centered on the
 * origin along the Z axis.
 * @param segments The number of fragments used to approximate the circle (OpenSCAD's `$fn`). When `null`, the
 * resolution defined by `$fa`/`$fs` is used.
 */
@Composable
fun Cylinder(
  height: Number,
  radius: Number? = null,
  topRadius: Number? = null,
  diameter: Number? = null,
  topDiameter: Number? = null,
  center: Boolean = false,
  segments: Int? = null,
) {
  val isRadius = radius != null || topRadius != null
  val isDiameter = diameter != null || topDiameter != null
  check(!(isRadius && isDiameter)) { "Cannot mix radius/topRadius with diameter/topDiameter" }
  check(topRadius == null || radius != null) {
    "topRadius requires radius (radius is the bottom, topRadius the top)"
  }
  check(topDiameter == null || diameter != null) {
    "topDiameter requires diameter (diameter is the bottom, topDiameter the top)"
  }

  val args = buildList {
    add("h = ${height.formatted()}")
    if (topRadius != null) {
      add("r1 = ${radius!!.formatted()}")
      add("r2 = ${topRadius.formatted()}")
    } else if (radius != null) {
      add("r = ${radius.formatted()}")
    }
    if (topDiameter != null) {
      add("d1 = ${diameter!!.formatted()}")
      add("d2 = ${topDiameter.formatted()}")
    } else if (diameter != null) {
      add("d = ${diameter.formatted()}")
    }
    if (center) add("center = true")
    segments?.let { add("\$fn = $it") }
  }
  Line("cylinder(${args.joinToString(", ")});")
}
