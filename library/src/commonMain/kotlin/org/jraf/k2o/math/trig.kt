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

package org.jraf.k2o.math

import kotlin.math.PI

/** Converts an angle in degrees to radians. */
fun degreesToRadians(degrees: Number) = degrees.toDouble() * (PI / 180.0)

/** Converts an angle in radians to degrees. */
fun radiansToDegrees(radians: Number) = radians.toDouble() * (180.0 / PI)

/**
 * Returns the sine of an angle expressed in **degrees**, matching OpenSCAD's `sin()` (unlike [kotlin.math.sin], which
 * works in radians).
 */
fun sin(degrees: Number) = kotlin.math.sin(degreesToRadians(degrees))

/**
 * Returns the cosine of an angle expressed in **degrees**, matching OpenSCAD's `cos()` (unlike [kotlin.math.cos],
 * which works in radians).
 */
fun cos(degrees: Number) = kotlin.math.cos(degreesToRadians(degrees))

/**
 * Returns the arctangent of [x] as an angle in **degrees**, matching OpenSCAD's `atan()` (unlike [kotlin.math.atan],
 * which returns radians).
 */
fun atan(x: Number) = radiansToDegrees(kotlin.math.atan(x.toDouble()))
