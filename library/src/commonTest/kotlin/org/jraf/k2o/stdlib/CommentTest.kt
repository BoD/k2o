package org.jraf.k2o.stdlib

import org.jraf.k2o.test.renderOpenScad
import kotlin.test.Test
import kotlin.test.assertEquals

class CommentTest {
  @Test
  fun withLeadingBlankLineByDefault() {
    assertEquals(
      """

      // note
      """.trimIndent(),
      renderOpenScad {
        Comment("note")
      },
    )
  }

  @Test
  fun withoutLeadingBlankLine() {
    assertEquals(
      "// note",
      renderOpenScad {
        Comment("note", newLine = false)
      },
    )
  }
}

