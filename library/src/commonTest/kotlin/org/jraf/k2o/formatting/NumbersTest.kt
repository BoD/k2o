package org.jraf.k2o.formatting

import kotlin.test.Test
import kotlin.test.assertEquals

class NumbersTest {
  @Test
  fun integralNumbersKeepIntegerFormat() {
    assertEquals("12", 12.formatted())
    assertEquals("12", 12L.formatted())
    assertEquals("12", 12.0.formatted())
    assertEquals("12", 12f.formatted())
  }

  @Test
  fun decimalNumbersAreRoundedToThreePlacesAndTrimmed() {
    assertEquals("1.235", 1.2346.formatted())
    assertEquals("1.2", 1.2.formatted())
    assertEquals("1.25", 1.25f.formatted())
    assertEquals("1.005", 1.005.formatted())
    assertEquals("0.005", 0.0049.formatted())
  }

  @Test
  fun negativeDecimalNumbersAreRoundedToThreePlacesAndTrimmed() {
    assertEquals("-1.235", (-1.2346).formatted())
    assertEquals("-1.2", (-1.2).formatted())
    assertEquals("0", (-0.0004).formatted())
  }
}
