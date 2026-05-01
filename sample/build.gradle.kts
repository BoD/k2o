plugins {
  alias(libs.plugins.kotlin.jvm)
  alias(libs.plugins.compose)
  alias(libs.plugins.kotlin.compose)
}

kotlin {
  jvmToolchain(17)
}

dependencies {
  // K2o
  implementation(project(":k2o"))
}
