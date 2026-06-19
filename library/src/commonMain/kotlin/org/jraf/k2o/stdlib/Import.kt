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

/**
 * [Imports](https://en.wikibooks.org/wiki/OpenSCAD_User_Manual/Importing_Geometry#import) geometry from an external
 * file (such as an STL, OFF, SVG or DXF) as an object.
 *
 * @param path The path to the file to import, either relative to the generated `.scad` file, or absolute.
 * @param center When `true`, the imported geometry is centered on the origin; when `false` (the default), its original
 * coordinates are kept.
 */
@Composable
fun Import(path: String, center: Boolean = false) {
  Line("import(\"$path\"${if (center) ", center = true" else ""});")
}
