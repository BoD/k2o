plugins {
  kotlin("multiplatform").apply(false)
  kotlin("jvm").apply(false)
}

allprojects {
  group = "org.jraf"
  version = "1.0.0"
}

// `./gradlew refreshVersions` to update dependencies
