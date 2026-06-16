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
import org.jraf.k2o.dsl.RawText
import org.jraf.k2o.dsl.indent
import org.jraf.k2o.dsl.withBraces
import org.jraf.k2o.formatting.formatted

/**
 * [Linear-extrudes](https://en.wikibooks.org/wiki/OpenSCAD_User_Manual/2D_to_3D_Extrusion#linear_extrude) a 2D shape
 * straight up the Z axis into a 3D solid of the given height.
 *
 * @param height The height of the extrusion along the Z axis.
 * @param content The 2D children to extrude.
 */
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

/**
 * [Linear-extrudes](https://en.wikibooks.org/wiki/OpenSCAD_User_Manual/2D_to_3D_Extrusion#linear_extrude) a 2D shape
 * into a 3D solid, with full control over twisting, scaling and resolution.
 *
 * @param height The height of the extrusion: how far the shape travels along [direction].
 * @param direction The direction in which the shape is extruded, as a vector. When `null`, the shape is extruded
 * straight up the Z axis. Only the direction of the vector matters: its length is ignored, since [height] is what sets
 * how far the shape travels.
 * @param center When `false` (the default), the solid grows up from the XY plane. When `true`, it is centered on the
 * plane.
 * @param twist The total rotation, in degrees, applied from bottom to top.
 * @param scale The scale factor applied to the top face (`1` keeps the original size).
 * @param slices The number of intermediate layers, mainly relevant when [twist] is used.
 * @param segments The number of fragments (OpenSCAD's `$fn`) used for the extrusion.
 * @param convexity A hint for the preview renderer, equal to the maximum number of faces a ray crosses through the
 * solid.
 * @param content The 2D children to extrude.
 */
@Composable
fun linearExtrude(
  height: Number,
  direction: Vect? = null,
  center: Boolean = false,
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
    direction?.let {
      RawText(",")
      Line("v = $it")
    }
    if (center) {
      RawText(",")
      Line("center = true")
    }
    twist?.let {
      RawText(",")
      Line("twist = ${it.formatted()}")
    }
    scale?.let {
      RawText(",")
      Line("scale = ${it.formatted()}")
    }
    slices?.let {
      RawText(",")
      Line("slices = $it")
    }
    segments?.let {
      RawText(",")
      Line("segments = $it")
    }
    convexity?.let {
      RawText(",")
      Line("convexity = $it")
    }
  }
  Line(")")
  withBraces {
    content()
  }
}
