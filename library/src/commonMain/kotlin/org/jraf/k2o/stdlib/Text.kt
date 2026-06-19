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
 * Horizontal alignment of a [Text] block relative to its origin, mapping to OpenSCAD's `halign`.
 */
enum class TextHorizontalAlignment(internal val value: String) {
  Left("left"),
  Center("center"),
  Right("right"),
}

/**
 * Vertical alignment of a [Text] block relative to its origin, mapping to OpenSCAD's `valign`.
 */
enum class TextVerticalAlignment(internal val value: String) {
  Top("top"),
  Center("center"),
  Baseline("baseline"),
  Bottom("bottom"),
}

/**
 * Layout direction of a [Text] block, mapping to OpenSCAD's `direction`.
 */
enum class TextDirection(internal val value: String) {
  LeftToRight("ltr"),
  RightToLeft("rtl"),
  TopToBottom("ttb"),
  BottomToTop("btt"),
}

/**
 * Creates a 2D [text](https://en.wikibooks.org/wiki/OpenSCAD_User_Manual/Text) object that can be extruded into 3D
 * (for example with [linearExtrude]) or used in 2D operations.
 *
 * @param text The string to render.
 * @param size The nominal height of the text, in the same units as the rest of the design.
 * @param font The font to use, as a name optionally followed by a style, e.g. `"Liberation Sans:style=Bold"`.
 * @param horizontalAlignment The horizontal alignment of the text relative to the origin.
 * @param verticalAlignment The vertical alignment of the text relative to the origin.
 * @param spacing A factor applied to the default spacing between letters (`1` is the default spacing).
 * @param direction The layout direction of the text.
 * @param language The two-letter language code, used for language-specific letter forms, e.g. `"en"`.
 * @param script The script of the text, e.g. `"latin"`.
 * @param segments The number of fragments used to approximate the curves of the glyphs (OpenSCAD's `$fn`). When
 * `null`, the resolution defined by `$fa`/`$fs` is used.
 */
@Composable
fun Text(
  text: String,
  size: Number? = null,
  font: String? = null,
  horizontalAlignment: TextHorizontalAlignment? = null,
  verticalAlignment: TextVerticalAlignment? = null,
  spacing: Number? = null,
  direction: TextDirection? = null,
  language: String? = null,
  script: String? = null,
  segments: Int? = null,
) {
  val args = buildList {
    add("\"$text\"")
    size?.let { add("size = ${it.formatted()}") }
    font?.let { add("font = \"$it\"") }
    horizontalAlignment?.let { add("halign = \"${it.value}\"") }
    verticalAlignment?.let { add("valign = \"${it.value}\"") }
    spacing?.let { add("spacing = ${it.formatted()}") }
    direction?.let { add("direction = \"${it.value}\"") }
    language?.let { add("language = \"$it\"") }
    script?.let { add("script = \"$it\"") }
    segments?.let { add("\$fn = $it") }
  }
  Line("text(${args.joinToString(", ")});")
}
