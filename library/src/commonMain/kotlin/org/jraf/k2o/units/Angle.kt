/*
 * This source is part of the
 *      _____  ___   ____
 *  __ / / _ \/ _ | / __/___  _______ _
 * / // / , _/ __ |/ _/_/ _ \/ __/ _ `/
 * \___/_/|_/_/ |_/_/ (_)___/_/  \_, /
 *                              /___/
 * repository.
 *
 * Copyright (C) 2026-present Benoit 'BoD' Lubek (BoD@JRAF.org)
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

package org.jraf.k2o.units

import org.jraf.k2o.formatting.formatted
import org.jraf.k2o.math.radiansToDegrees
import kotlin.jvm.JvmInline

@JvmInline
value class Angle private constructor(val degrees: Double) : Comparable<Angle> {
  constructor(degrees: Number) : this(degrees.toDouble())

  operator fun unaryMinus(): Angle = Angle(-this.degrees)

  operator fun plus(angle: Angle): Angle = Angle(this.degrees + angle.degrees)
  operator fun minus(angle: Angle): Angle = Angle(this.degrees - angle.degrees)

  operator fun times(factor: Number): Angle = Angle(this.degrees * factor)
  operator fun div(factor: Number): Angle = Angle(this.degrees / factor)
  operator fun rem(divisor: Number): Angle = Angle(this.degrees % divisor)

  operator fun div(factor: Angle): Number = this.degrees / factor.degrees
  operator fun rem(divisor: Angle): Angle = Angle(this.degrees % divisor.degrees)

  override operator fun compareTo(other: Angle): Int {
    return this.degrees.compareTo(other.degrees)
  }

  override fun toString(): String {
    return degrees.formatted()
  }

  companion object {
    val Number.deg: Angle
      get() = Angle(this)

    val Number.rad: Angle
      get() = Angle(radiansToDegrees(this))

    operator fun Number.times(angle: Angle): Angle = angle * this

    val Zero: Angle = 0.deg

    val FullCircle: Angle = 360.deg
  }
}
