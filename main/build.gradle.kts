plugins {
  kotlin("jvm")
}

kotlin {
  jvmToolchain(17)
}

dependencies {
  // Logging
  implementation("org.jraf:klibnanolog:_")

  // K2o
  implementation(project(":k2o"))
}
