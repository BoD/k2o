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
import org.jraf.k2o.dsl.Text
import org.jraf.k2o.dsl.indent
import org.jraf.k2o.dsl.withBraces
import org.jraf.k2o.formatting.formatted

@Composable
fun linearExtrude(
  height: Number,
  content: @Composable () -> Unit,
) {
  Line("linear_extrude(${height.formatted()})")
  withBraces {
    content()
  }
}

@Composable
fun linearExtrude(
  height: Number,
  v: Vect? = null,
  center: Boolean? = null,
  twist: Number? = null,
  scale: Number? = null,
  slices: Int? = null,
  segments: Int? = null,
  convexity: Int? = null,
  content: @Composable () -> Unit,
) {
  Line("linear_extrude(")
  indent {
    Line("height = ${height.formatted()}")
    v?.let {
      Text(",")
      Line("v = $it")
    }
    center?.let {
      Text(",")
      Line("center = $it")
    }
    twist?.let {
      Text(",")
      Line("twist = ${it.formatted()}")
    }
    scale?.let {
      Text(",")
      Line("scale = ${it.formatted()}")
    }
    slices?.let {
      Text(",")
      Line("slices = $it")
    }
    segments?.let {
      Text(",")
      Line("segments = $it")
    }
    convexity?.let {
      Text(",")
      Line("convexity = $it")
    }
  }
  Line(")")
  withBraces {
    content()
  }
}
