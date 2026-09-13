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
import org.jraf.k2o.units.Angle

/**
 * [Rotates](https://en.wikibooks.org/wiki/OpenSCAD_User_Manual/Transformations#rotate) its children around each axis.
 * Rotations are applied around X, then Y, then Z.
 *
 * @param x The rotation around the X axis.
 * @param y The rotation around the Y axis.
 * @param z The rotation around the Z axis.
 * @param content The children to rotate.
 */
@Composable
fun rotate(
  x: Angle = Angle.Zero,
  y: Angle = Angle.Zero,
  z: Angle = Angle.Zero,
  content: @Composable () -> Unit,
) {
  Line("rotate([$x, $y, $z])")
  withBraces {
    content()
  }
}

/**
 * [Rotates](https://en.wikibooks.org/wiki/OpenSCAD_User_Manual/Transformations#rotate) its children around the Z axis
 * by the given angle.
 *
 * @param angle The rotation around the Z axis.
 * @param content The children to rotate.
 */
@Composable
fun rotate(
  angle: Angle,
  content: @Composable () -> Unit,
) {
  Line("rotate($angle)")
  withBraces {
    content()
  }
}
