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
import kotlin.jvm.JvmInline
import kotlin.math.sqrt

@JvmInline
value class Length private constructor(val millimeters: Double) : Comparable<Length> {
  constructor(millimeters: Number) : this(millimeters.toDouble())

  operator fun unaryMinus(): Length = Length(-this.millimeters)

  operator fun plus(length: Length): Length = Length(this.millimeters + length.millimeters)
  operator fun minus(length: Length): Length = Length(this.millimeters - length.millimeters)

  operator fun times(factor: Number): Length = Length(this.millimeters * factor)
  operator fun div(factor: Number): Length = Length(this.millimeters / factor)
  operator fun rem(divisor: Number): Length = Length(this.millimeters % divisor)

  operator fun div(factor: Length): Number = this.millimeters / factor.millimeters
  operator fun rem(divisor: Length): Length = Length(this.millimeters % divisor.millimeters)

  override operator fun compareTo(other: Length): Int {
    return this.millimeters.compareTo(other.millimeters)
  }

  override fun toString(): String {
    return millimeters.formatted()
  }

  companion object {
    val Number.mm: Length
      get() = Length(this)

    val Number.cm: Length
      get() = Length(this * 10)

    operator fun Number.times(length: Length): Length = length * this

    val Zero: Length = 0.mm

    val Smallest = 0.001.mm

    /**
     * Returns the distance between two points in 2D space.
     * The points are represented as pairs of [Length] values (x, y).
     * The distance is calculated using the Pythagorean theorem.
     * @param point1 The first point as a pair of [Length] values (x1, y1).
     * @param point2 The second point as a pair of [Length] values (x2, y2).
     * @return The distance between the two points as a [Length] value.
     */
    fun distance(point1: Pair<Length, Length>, point2: Pair<Length, Length>): Length {
      val (x1, y1) = point1
      val (x2, y2) = point2
      val dx = (x2 - x1).millimeters
      val dy = (y2 - y1).millimeters
      return Length(sqrt((dx * dx + dy * dy)))
    }

    /**
     * Returns the length of the hypotenuse of a right triangle given the lengths of the other two sides.
     * @param side1 The length of the first side.
     * @param side2 The length of the second side.
     * @return The length of the hypotenuse.
     */
    fun pythagoreanHypotenuse(side1: Length, side2: Length): Length {
      return Length(sqrt((side1.millimeters * side1.millimeters + side2.millimeters * side2.millimeters)))
    }

    /**
     * Returns the length of the second side of a right triangle given the lengths of the hypotenuse and the first side.
     * @param hypotenuse The length of the hypotenuse.
     * @param side The length of the first side.
     * @return The length of the second side.
     */
    fun pythagoreanSide(hypotenuse: Length, side: Length): Length {
      return Length(sqrt((hypotenuse.millimeters * hypotenuse.millimeters - side.millimeters * side.millimeters)))
    }
  }
}
