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
import org.jraf.k2o.dsl.indent

/**
 * Creates a 3D [polyhedron](https://en.wikibooks.org/wiki/OpenSCAD_User_Manual/Primitive_Solids#polyhedron) from a
 * list of points and the faces connecting them. This is the most general 3D primitive, able to describe any shape.
 *
 * @param points The `(x, y, z)` vertices of the polyhedron.
 * @param faces Each face as a list of indices into [points]. The indices of a face must be ordered clockwise when
 * looking at the face from the outside.
 * @param convexity A hint for the preview renderer, equal to the maximum number of faces a ray could cross when
 * passing through the polyhedron.
 */
@Composable
fun Polyhedron(
  points: List<Triple<Number, Number, Number>>,
  faces: List<List<Int>>,
  convexity: Int? = null,
) {
  Line("polyhedron(")
  indent {
    Line("points = [")
    indent {
      for ((x, y, z) in points) {
        Line("${Vect(x, y, z)},")
      }
    }
    Line("],")
    Line("faces = [")
    indent {
      for (face in faces) {
        Line("[${face.joinToString(", ")}],")
      }
    }
    Line(if (convexity == null) "]" else "],")
    if (convexity != null) {
      Line("convexity = $convexity")
    }
  }
  Line(");")
}
