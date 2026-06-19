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

package org.jraf.k2o.dsl

import androidx.compose.runtime.AbstractApplier
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ControlledComposition
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.Recomposer
import androidx.compose.runtime.staticCompositionLocalOf
import kotlinx.io.Buffer
import kotlinx.io.RawSink
import kotlinx.io.Sink
import kotlinx.io.buffered
import kotlinx.io.readByteArray
import kotlinx.io.readString
import kotlinx.io.writeString
import org.jraf.k2o.VERSION
import org.jraf.k2o.dsl.OpenScad.Element
import org.jraf.k2o.dsl.OpenScad.NewLineElement
import org.jraf.k2o.formatting.formatted
import org.jraf.k2o.stdlib.Comment
import kotlin.coroutines.EmptyCoroutineContext

/**
 * Accumulates the [Element]s produced while running a DSL block and renders them, handling indentation, to a [Sink].
 *
 * You rarely need to use this type directly: [openScad] creates one for you and exposes it through [LocalOpenScad].
 * It is relevant mostly when writing low-level building blocks on top of [RawText], [Line], [Indent] and [Unindent].
 */
class OpenScad {
  private val elements = mutableListOf<Element>()

  /** Appends a raw [element] to the output being built. */
  fun add(element: Element) {
    elements.add(element)
  }

  /** Renders all accumulated elements to [sink], applying the indentation accumulated by [Indent]/[Unindent]. */
  fun write(sink: Sink) {
    var indent = 0
    for ((i, element) in elements.withIndex()) {
      when (element) {
        is Indent -> indent++
        is Unindent -> indent--
      }
      check(indent >= 0) { "Tried to unindent more than indented" }
      if (element is NewLineElement && i > 0) {
        sink.writeString("\n")
        repeat(indent) {
          sink.writeString("  ")
        }
      }
      sink.writeString(element.content)
    }
  }

  interface Element {
    val content: String
  }

  /**
   * Marker interface for elements that will start a new line when written.
   */
  interface NewLineElement : Element
}

private object Indent : Element {
  // No content, just a marker for indentation
  override val content = ""
}

/**
 * Increases the indentation level of everything emitted afterwards by one, until a matching [Unindent]. Low-level
 * building block, mostly useful when writing custom DSL functions.
 */
@Composable
fun Indent() {
  LocalOpenScad.current.add(Indent)
}

private object Unindent : Element {
  // No output, just a marker for unindenting
  override val content = ""
}

/**
 * Decreases the indentation level by one, undoing a previous [Indent]. Low-level building block, mostly useful when
 * writing custom DSL functions.
 */
@Composable
fun Unindent() {
  LocalOpenScad.current.add(Unindent)
}

private class TextElement(override val content: String) : Element

/**
 * Appends raw [content] to the current line, without starting a new one. Low-level building block; prefer [Line] to
 * emit a full line.
 */
@Composable
fun RawText(content: String) {
  LocalOpenScad.current.add(TextElement(content))
}

private class LineElement(override val content: String) : NewLineElement

/**
 * Emits [content] on a new line, indented at the current level. This is the main building block for writing custom
 * DSL functions.
 */
@Composable
fun Line(content: String) {
  LocalOpenScad.current.add(LineElement(content))
}

/**
 * Wraps [content] in a ` { ... }` block, emitting the opening brace on the current line, the [content] indented one
 * level, and the closing brace on its own line. Handy when writing custom DSL functions that take children.
 */
@Composable
fun withBraces(content: @Composable () -> Unit) {
  with(LocalOpenScad.current) {
    RawText(" {")
    indent {
      content()
    }
    Line("}")
  }
}

/**
 * Runs [content] with the indentation level increased by one, pairing [Indent] and [Unindent] automatically.
 */
@Composable
fun indent(content: @Composable () -> Unit) {
  with(LocalOpenScad.current) {
    Indent()
    content()
    Unindent()
  }
}

/**
 * Entry point of the DSL: runs the given [content] and writes the resulting OpenSCAD code to [sink].
 *
 * The output starts with a generated header that sets the `$fa` and `$fs` special variables, followed by the code
 * produced by [content]. All other k2o composables must be called, directly or transitively, from within this block.
 *
 * @param sink Where the generated OpenSCAD code is written. Defaults to standard output.
 * @param fa The minimum angle (in degrees) for a fragment, written as `$fa`. Smaller values give smoother curves. When `null` (the default), the `$fa` variable is not set.
 * @param fs The minimum size of a fragment, written as `$fs`. Smaller values give smoother curves. When `null` (the default), the `$fs` variable is not set.
 * @param includeVersionHeader When `true` (the default), a comment with the k2o version is added at the top of the generated OpenSCAD code.
 * @param content The design to render, expressed with the k2o DSL.
 */
fun openScad(
  sink: Sink = StdoutSink,
  fa: Double? = null,
  fs: Double? = null,
  includeVersionHeader: Boolean = true,
  content: @Composable () -> Unit,
) {
  val openScad = OpenScad()
  ControlledComposition(NoOpApplier(), Recomposer(EmptyCoroutineContext)).setContent {
    CompositionLocalProvider(LocalOpenScad provides openScad) {
      var addNewLine = false
      if (includeVersionHeader) {
        Comment("Generated by k2o $VERSION", newSection = false)
        addNewLine = true
      }
      if (fa != null) {
        Line($$"$fa = $${fa.formatted()};")
        addNewLine = true
      }
      if (fs != null) {
        Line($$"$fs = $${fs.formatted()};")
        addNewLine = true
      }
      if (addNewLine) {
        Line("")
      }
      content()
    }
  }
  openScad.write(sink)
  sink.flush()
}

/**
 * Renders the given [content] to a string containing the generated OpenSCAD code.
 *
 * @param fa The minimum angle (in degrees) for a fragment, written as `$fa`. Smaller values give smoother curves. When `null` (the default), the `$fa` variable is not set.
 * @param fs The minimum size of a fragment, written as `$fs`. Smaller values give smoother curves. When `null` (the default), the `$fs` variable is not set.
 * @param includeVersionHeader If `true` a comment with the k2o version is added at the top of the generated OpenSCAD code (default: `false`).
 * @param content The design to render, expressed with the k2o DSL.
 */
fun renderOpenScad(
  fa: Double? = null,
  fs: Double? = null,
  includeVersionHeader: Boolean = false,
  content: @Composable () -> Unit,
): String {
  val buffer = Buffer()
  openScad(
    sink = buffer,
    fa = fa,
    fs = fs,
    includeVersionHeader = includeVersionHeader,
    content = content,
  )
  return buffer.readString()
}

private class NoOpApplier : AbstractApplier<Unit>(Unit) {
  override fun insertTopDown(index: Int, instance: Unit) {}
  override fun insertBottomUp(index: Int, instance: Unit) {}
  override fun remove(index: Int, count: Int) {}
  override fun move(from: Int, to: Int, count: Int) {}
  override fun onClear() {}
}

/**
 * The [OpenScad] instance for the current [openScad] block, made available to composables without having to pass it
 * explicitly. Custom DSL functions read it (via `LocalOpenScad.current`) to emit their output.
 */
val LocalOpenScad: ProvidableCompositionLocal<OpenScad> = staticCompositionLocalOf { OpenScad() }

internal val StdoutSink: Sink = StdoutRawSink.buffered()

private object StdoutRawSink : RawSink {
  override fun write(source: Buffer, byteCount: Long) {
    var remainingByteCount = byteCount
    while (remainingByteCount > 0) {
      val bytesToRead = minOf(remainingByteCount, Int.MAX_VALUE.toLong()).toInt()
      print(source.readByteArray(bytesToRead).decodeToString())
      remainingByteCount -= bytesToRead
    }
  }

  override fun flush() = Unit

  override fun close() = Unit
}
