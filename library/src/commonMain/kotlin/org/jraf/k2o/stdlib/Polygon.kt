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
import org.jraf.k2o.dsl.Indent
import org.jraf.k2o.dsl.Line
import org.jraf.k2o.dsl.Unindent

/**
 * Creates a 2D [polygon](https://en.wikibooks.org/wiki/OpenSCAD_User_Manual/2D_Primitives#polygon) from a list of
 * points, connected in order with the last point joined back to the first.
 *
 * @param points The `(x, y)` vertices of the polygon, in order.
 * @param paths One or more closed paths, each given as a list of indices into [points], in the order they should be
 * connected. Use this to describe polygons with holes (the first path is the outline, the others are holes). When
 * `null`, a single path using all points in order is assumed.
 * @param convexity A hint for the preview renderer, equal to the maximum number of edges a line could cross when
 * passing through the polygon.
 */
@Composable
fun Polygon(
  vararg points: Pair<Number, Number>,
  paths: List<List<Int>>? = null,
  convexity: Int? = null,
) {
  Line("polygon([")
  Indent()
  for ((x, y) in points) {
    Line("${Vect(x, y)},")
  }
  Unindent()
  val trailing = buildString {
    if (paths != null) {
      append(", paths = [")
      append(paths.joinToString(", ") { path -> "[${path.joinToString(", ")}]" })
      append("]")
    }
    if (convexity != null) {
      append(", convexity = $convexity")
    }
  }
  Line("]$trailing);")
}
