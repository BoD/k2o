import com.gradleup.librarian.gradle.Librarian

plugins {
  kotlin("multiplatform")
  id("org.jetbrains.compose")
  kotlin("plugin.compose")
}

kotlin {
  jvm()

  sourceSets {
    commonMain {
      dependencies {
        // Compose
        api(compose.runtime)

        // Logging
        implementation("org.jraf.klibnanolog:klibnanolog:_")

        // Kotlinx IO
        api("org.jetbrains.kotlinx:kotlinx-io-core:_")
      }
    }

    jvmMain {
      dependencies {
        // Not sure why this is needed, but Compose JVM whines without it
        implementation(AndroidX.collection)
      }
    }
  }
}

Librarian.module(project)
