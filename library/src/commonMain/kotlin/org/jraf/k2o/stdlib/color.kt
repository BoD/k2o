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

class Color {
  companion object {
    const val RED = "red"
    const val GREEN = "green"
    const val BLUE = "blue"
    const val YELLOW = "yellow"
    const val CYAN = "cyan"
    const val MAGENTA = "magenta"
    const val BLACK = "black"
    const val WHITE = "white"
    const val GRAY = "gray"
    const val ORANGE = "orange"
  }
}
