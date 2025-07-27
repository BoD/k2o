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
