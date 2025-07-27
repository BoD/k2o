plugins {
  kotlin("multiplatform").apply(false)
  kotlin("jvm").apply(false)
  id("org.jetbrains.compose").apply(false)
  kotlin("plugin.compose").apply(false)
}

allprojects {
  group = "org.jraf"
  version = "1.0.0"
}

// `./gradlew refreshVersions` to update dependencies
