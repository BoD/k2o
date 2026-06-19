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
import org.jraf.k2o.dsl.withBraces
import org.jraf.k2o.formatting.formatted

/**
 * [Rotates](https://en.wikibooks.org/wiki/OpenSCAD_User_Manual/Transformations#rotate) its children around each axis,
 * in degrees. Rotations are applied around X, then Y, then Z.
 *
 * @param x The rotation around the X axis, in degrees.
 * @param y The rotation around the Y axis, in degrees.
 * @param z The rotation around the Z axis, in degrees.
 * @param content The children to rotate.
 */
@Composable
fun rotate(
  x: Number = 0,
  y: Number = 0,
  z: Number = 0,
  content: @Composable () -> Unit,
) {
  Line("rotate(${Vect(x, y, z)})")
  withBraces {
    content()
  }
}

/**
 * [Rotates](https://en.wikibooks.org/wiki/OpenSCAD_User_Manual/Transformations#rotate) its children around the Z axis
 * by the given angle, in degrees.
 *
 * @param angle The rotation around the Z axis, in degrees.
 * @param content The children to rotate.
 */
@Composable
fun rotate(
  angle: Number,
  content: @Composable () -> Unit,
) {
  Line("rotate(${angle.formatted()})")
  withBraces {
    content()
  }
}
