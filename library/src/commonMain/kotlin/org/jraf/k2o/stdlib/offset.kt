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
 * [Offsets](https://en.wikibooks.org/wiki/OpenSCAD_User_Manual/Transformations#offset) a 2D shape by a radius,
 * growing it (positive [radius]) or shrinking it (negative [radius]) with rounded corners.
 *
 * @param radius The radius to offset by. Positive grows the shape, negative shrinks it.
 * @param content The 2D children to offset.
 */
@Composable
fun offset(
  radius: Number,
  content: @Composable () -> Unit,
) {
  Line("offset(r = ${radius.formatted()})")
  withBraces {
    content()
  }
}

/**
 * [Offsets](https://en.wikibooks.org/wiki/OpenSCAD_User_Manual/Transformations#offset) a 2D shape by a constant
 * distance, keeping sharp corners by default.
 *
 * @param delta The distance to offset by. Positive grows the shape, negative shrinks it.
 * @param chamfer When `true`, outer corners are chamfered (cut off) instead of kept sharp.
 * @param content The 2D children to offset.
 */
@Composable
fun offset(
  delta: Number,
  chamfer: Boolean = false,
  content: @Composable () -> Unit,
) {
  Line("offset(delta = ${delta.formatted()}${if (chamfer) ", chamfer = true" else ""})")
  withBraces {
    content()
  }
}
