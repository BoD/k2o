package org.jraf.k2o.stdlib

import org.jraf.k2o.test.renderOpenScad
import kotlin.test.Test
import kotlin.test.assertEquals

class TextTest {
  @Test
  fun textOnly() {
    assertEquals(
      """text("Hello");""",
      renderOpenScad {
        Text("Hello")
      },
    )
  }

  @Test
  fun sizeAndAlignment() {
    assertEquals(
      """text("Hi", size = 8, halign = "center", valign = "top");""",
      renderOpenScad {
        Text("Hi", size = 8, horizontalAlignment = TextHorizontalAlignment.Center, verticalAlignment = TextVerticalAlignment.Top)
      },
    )
  }

  @Test
  fun allParameters() {
    assertEquals(
      $$"""text("Hi", size = 8, font = "Liberation Sans:style=Bold", halign = "right", valign = "baseline", spacing = 1.5, direction = "ltr", language = "en", script = "latin", $fn = 32);""",
      renderOpenScad {
        Text(
          text = "Hi",
          size = 8,
          font = "Liberation Sans:style=Bold",
          horizontalAlignment = TextHorizontalAlignment.Right,
          verticalAlignment = TextVerticalAlignment.Baseline,
          spacing = 1.5,
          direction = TextDirection.LeftToRight,
          language = "en",
          script = "latin",
          segments = 32,
        )
      },
    )
  }
}
