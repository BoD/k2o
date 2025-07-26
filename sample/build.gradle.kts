plugins {
  kotlin("jvm")
}

kotlin {
  jvmToolchain(17)
}

dependencies {
  // K2o
  implementation(project(":k2o"))
}
