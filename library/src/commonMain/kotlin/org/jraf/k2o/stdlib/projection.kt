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

import androidx.compose.runtime.Composable
import org.jraf.k2o.dsl.Line
import org.jraf.k2o.dsl.withBraces

/**
 * [Projects](https://en.wikibooks.org/wiki/OpenSCAD_User_Manual/3D_to_2D_Projection) its 3D children onto the XY
 * plane, producing a 2D shape.
 *
 * @param cut When `false` (the default), the result is the "shadow" of the children: everything projected straight
 * down onto the plane. When `true`, the result is the cross-section where the children intersect the XY plane (a
 * slice).
 * @param convexity A hint for the preview renderer, equal to the maximum number of edges a ray could cross when
 * passing through the resulting 2D shape.
 * @param content The 3D children to project.
 */
@Composable
fun projection(
  cut: Boolean = false,
  convexity: Int? = null,
  content: @Composable () -> Unit,
) {
  val args = buildList {
    if (cut) add("cut = true")
    convexity?.let { add("convexity = $it") }
  }
  Line("projection(${args.joinToString(", ")})")
  withBraces {
    content()
  }
}
