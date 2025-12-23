plugins {
  kotlin("jvm")
  id("org.jetbrains.compose")
  kotlin("plugin.compose")
}

kotlin {
  jvmToolchain(17)
}

dependencies {
  // K2o
  implementation(project(":k2o"))
}

// See https://github.com/BoD/k2o/pull/4
configurations.named { it == "mainSourceElements" }.configureEach {
  attributes {
    attribute(Usage.USAGE_ATTRIBUTE, objects.named(Usage::class.java, "sources"))
  }
}