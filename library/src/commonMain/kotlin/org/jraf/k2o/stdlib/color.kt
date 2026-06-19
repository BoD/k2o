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
 * Applies a [color](https://en.wikibooks.org/wiki/OpenSCAD_User_Manual/Transformations#color) to its children for
 * preview rendering (colors are ignored when exporting to most formats).
 *
 * @param color A color name (e.g. one of the [Color] constants) or any value accepted by OpenSCAD's `color()`, such
 * as a `"#rrggbb"` hex string.
 * @param content The children to color.
 */
@Composable
fun color(
  color: String,
  content: @Composable () -> Unit,
) {
  Line("color(\"$color\")")
  withBraces {
    content()
  }
}

/**
 * Convenience constants for the standard OpenSCAD color names, for use with [color].
 */
class Color {
  companion object {
    const val Red = "red"
    const val Green = "green"
    const val Blue = "blue"
    const val Yellow = "yellow"
    const val Cyan = "cyan"
    const val Magenta = "magenta"
    const val Black = "black"
    const val White = "white"
    const val Gray = "gray"
    const val Orange = "orange"
  }
}
