import com.gradleup.librarian.gradle.Librarian

plugins {
  alias(libs.plugins.kotlin.multiplatform)
  alias(libs.plugins.compose)
  alias(libs.plugins.kotlin.compose)
}

kotlin {
  jvm()

  sourceSets {
    commonMain {
      dependencies {
        // Compose
        api(libs.compose.runtime)

        // Logging
        implementation(libs.klibnanolog)

        // Kotlinx IO
        api(libs.kotlinx.io)
      }
    }

    jvmMain {
      dependencies {
        // Not sure why this is needed, but Compose JVM whines without it
        implementation(libs.androidx.collection)
      }
    }
  }

  compilerOptions {
    // See https://kotlinlang.org/docs/whatsnew-eap.html#support-for-collection-literals
    freeCompilerArgs.add("-Xcollection-literals")
  }
}

Librarian.module(project)
