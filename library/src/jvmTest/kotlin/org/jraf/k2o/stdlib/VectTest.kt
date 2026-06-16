package org.jraf.k2o.stdlib

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class VectTest {
  @Test
  fun oneElement() {
    assertEquals("[1]", Vect.of(1).toString())
  }

  @Test
  fun twoElements() {
    assertEquals("[1, 2.5]", Vect.of(1, 2.5).toString())
  }

  @Test
  fun threeElements() {
    assertEquals("[1, 2.5, 3]", Vect.of(1, 2.5, 3).toString())
  }

  @Test
  fun zElementWithoutYUsesZeroForY() {
    assertEquals("[1, 0, 3]", Vect(x = 1, z = 3).toString())
  }

  @Test
  fun noElementsFails() {
    assertFailsWith<IllegalStateException> {
      Vect.of()
    }
  }

  @Test
  fun moreThanThreeElementsFails() {
    assertFailsWith<IllegalStateException> {
      Vect.of(1, 2, 3, 4)
    }
  }
}
