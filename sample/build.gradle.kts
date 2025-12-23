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

configurations.named { it == "mainSourceElements" }.configureEach {
  attributes {
    attribute(Usage.USAGE_ATTRIBUTE, objects.named(Usage::class.java, "sources"))
  }
}