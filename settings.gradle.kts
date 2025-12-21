rootProject.name = "k2o-root"

pluginManagement {
  repositories {
    mavenCentral()
    gradlePluginPortal()
    maven("https://central.sonatype.com/repository/maven-snapshots/")
  }
}

dependencyResolutionManagement {
  @Suppress("UnstableApiUsage")
  repositories {
    mavenCentral()
    google()
    maven("https://central.sonatype.com/repository/maven-snapshots/")
  }
}

plugins {
  // See https://splitties.github.io/refreshVersions/
  id("de.fayard.refreshVersions") version "0.60.6"
}

include(
  ":library",
  ":sample",
)

project(":library").name = "k2o"
