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
 * Emits a [`use`](https://en.wikibooks.org/wiki/OpenSCAD_User_Manual/Include_Statement) statement, making the modules
 * and functions defined in another `.scad` file available (without executing its top-level geometry). Combine with
 * [Call] to invoke the imported modules.
 *
 * @param path The path to the `.scad` file to use, relative to the generated file.
 */
@Composable
fun Use(path: String) {
  Line("use <$path>;")
}
