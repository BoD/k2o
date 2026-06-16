package org.jraf.k2o.test

import androidx.compose.runtime.Composable
import kotlinx.io.Buffer
import kotlinx.io.readString
import org.jraf.k2o.dsl.openScad

fun renderOpenScad(content: @Composable () -> Unit): String {
  val buffer = Buffer()
  openScad(sink = buffer, content = content)
  return buffer.readString().substringAfter("\n\n")
}

