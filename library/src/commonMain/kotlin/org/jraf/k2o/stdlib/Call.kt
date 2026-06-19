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
import org.jraf.k2o.dsl.Indent
import org.jraf.k2o.dsl.Line
import org.jraf.k2o.dsl.Unindent
import org.jraf.k2o.dsl.withBraces
import org.jraf.k2o.formatting.formatted

/**
 * Calls an arbitrary OpenSCAD module by name with named arguments. Use this as an escape hatch to invoke modules that
 * k2o does not provide a dedicated function for, for example modules brought in via [Use] or [Import].
 *
 * Each argument value is rendered according to its runtime type: [String] values are quoted, [Number] values are
 * formatted, and anything else falls back to its `toString()`.
 *
 * @param module The name of the OpenSCAD module to call.
 * @param args The named arguments to pass, as `name to value` pairs.
 * @param content Optional children to nest inside the call's block. When `null`, the call is emitted as a plain
 * statement.
 */
@Composable
fun Call(module: String, vararg args: Pair<String, Any>, content: (@Composable () -> Unit)? = null) {
  Line("$module(")
  Indent()
  for ((name, value) in args) {
    val v = when (value) {
      is String -> "\"$value\""
      is Number -> value.formatted()
      else -> value.toString()
    }
    Line("$name = $v,")
  }
  Unindent()
  if (content != null) {
    Line(")")
    withBraces {
      content()
    }
  } else {
    Line(");")
  }
}
