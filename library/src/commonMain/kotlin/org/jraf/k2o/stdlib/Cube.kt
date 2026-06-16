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
 * Creates a [cube](https://en.wikibooks.org/wiki/OpenSCAD_User_Manual/Primitive_Solids#cube) (a box) with the
 * given dimensions.
 *
 * @param x The size along the X axis.
 * @param y The size along the Y axis.
 * @param z The size along the Z axis.
 * @param center When `false` (the default), the cube is placed in the first octant, with one corner at the origin.
 * When `true`, it is centered on the origin.
 */
@Composable
fun Cube(
  x: Number,
  y: Number,
  z: Number,
  center: Boolean = false,
) {
  if (center) {
    Line("cube([${x.formatted()}, ${y.formatted()}, ${z.formatted()}], center = true);")
  } else {
    Line("cube([${x.formatted()}, ${y.formatted()}, ${z.formatted()}]);")
  }
}

/**
 * Creates a [cube](https://en.wikibooks.org/wiki/OpenSCAD_User_Manual/Primitive_Solids#cube) with the same size on
 * all three axes.
 *
 * @param size The size along the X, Y and Z axes.
 * @param center When `false` (the default), the cube is placed in the first octant, with one corner at the origin.
 * When `true`, it is centered on the origin.
 */
@Composable
fun Cube(size: Number, center: Boolean = false) {
  if (center) {
    Line("cube(${size.formatted()}, center = true);")
  } else {
    Line("cube(${size.formatted()});")
  }
}
