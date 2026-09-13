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

import org.jraf.k2o.units.Angle
import org.jraf.k2o.units.Angle.Companion.deg
import kotlin.math.PI

/** Converts an angle in degrees to radians. */
internal fun degreesToRadians(degrees: Number) = degrees.toDouble() * (PI / 180.0)

/** Converts an angle in radians to degrees. */
internal fun radiansToDegrees(radians: Number) = radians.toDouble() * (180.0 / PI)

/**
 * Returns the sine of an angle.
 */
fun sin(angle: Angle): Number = kotlin.math.sin(degreesToRadians(angle.degrees))

/**
 * Returns the cosine of an angle.
 */
fun cos(angle: Angle): Number = kotlin.math.cos(degreesToRadians(angle.degrees))

/**
 * Returns the arctangent of [x] as an [Angle].
 */
fun atan(x: Number): Angle = radiansToDegrees(kotlin.math.atan(x.toDouble())).deg
