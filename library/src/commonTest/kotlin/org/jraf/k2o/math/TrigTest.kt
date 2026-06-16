package org.jraf.k2o.math

import kotlin.math.PI
import kotlin.test.Test
import kotlin.test.assertEquals

class TrigTest {
  @Test
  fun convertsBetweenDegreesAndRadians() {
    assertEquals(PI, degreesToRadians(180), absoluteTolerance = 0.000001)
    assertEquals(180.0, radiansToDegrees(PI), absoluteTolerance = 0.000001)
  }

  @Test
  fun trigonometricFunctionsUseDegrees() {
    assertEquals(1.0, sin(90), absoluteTolerance = 0.000001)
    assertEquals(0.0, cos(90), absoluteTolerance = 0.000001)
    assertEquals(45.0, atan(1), absoluteTolerance = 0.000001)
  }
}

