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
 * Creates a [sphere](https://en.wikibooks.org/wiki/OpenSCAD_User_Manual/Primitive_Solids#sphere) centered on the
 * origin.
 *
 * At most one of [radius] or [diameter] may be specified; specifying both throws. When neither is given, OpenSCAD's
 * default size is used.
 *
 * @param radius The radius of the sphere.
 * @param diameter The diameter of the sphere.
 * @param segments The number of fragments used to approximate the sphere (OpenSCAD's `$fn`). When `null`, the
 * resolution defined by `$fa`/`$fs` is used.
 */
@Composable
fun Sphere(
  radius: Number? = null,
  diameter: Number? = null,
  segments: Int? = null,
) {
  if (radius != null && diameter != null) {
    error("Only one of radius or diameter can be specified")
  }
  val sizeArg = when {
    radius != null -> "r = ${radius.formatted()}"
    diameter != null -> "d = ${diameter.formatted()}"
    else -> null
  }
  val segmentsArg = if (segments != null) "\$fn = $segments" else null
  val args = listOfNotNull(sizeArg, segmentsArg).joinToString(", ")
  Line("sphere($args);")
}
