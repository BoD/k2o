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

/**
 * [Resizes](https://en.wikibooks.org/wiki/OpenSCAD_User_Manual/Transformations#resize) its children to the given
 * bounding-box dimensions. A dimension of `0` leaves that axis unchanged (or auto-scales it, see [auto]).
 *
 * @param x The target size along the X axis, or `0` to leave it untouched.
 * @param y The target size along the Y axis, or `0` to leave it untouched.
 * @param z The target size along the Z axis, or `0` to leave it untouched.
 * @param auto When `true`, axes left at `0` are scaled by the same factor as the non-zero axes instead of being kept
 * at their original size.
 * @param content The children to resize.
 */
@Composable
fun resize(
  x: Number = 0,
  y: Number = 0,
  z: Number = 0,
  auto: Boolean,
  content: @Composable () -> Unit,
) {
  Line("resize(${Vect(x, y, z)}, auto = $auto)")
  withBraces {
    content()
  }
}
