plugins {
  kotlin("multiplatform")
  id("org.jetbrains.compose")
  kotlin("plugin.compose")
  id("maven-publish")
  id("org.jetbrains.dokka")
  id("signing")
}

// Generate Javadoc (Dokka) Jar
val dokkaHtmlJar = tasks.register<Jar>("dokkaHtmlJar") {
  archiveClassifier.set("javadoc")
  from("${layout.buildDirectory}/dokka")
  dependsOn(tasks.dokkaGenerate)
}

// Generate a Version.kt file with a constant for the version name
val generateVersionKtTask = tasks.register("generateVersionKt") {
  val outputDir = layout.buildDirectory.dir("generated/source/kotlin").get().asFile
  outputs.dir(outputDir)
  doFirst {
    val outputWithPackageDir = File(outputDir, "org/jraf/k2o").apply { mkdirs() }
    File(outputWithPackageDir, "Version.kt").writeText(
      """
        package org.jraf.k2o
        const val VERSION = "v${rootProject.version}"
      """.trimIndent()
    )
  }
}

kotlin {
  jvm()

  jvmToolchain(17)

  sourceSets {
    commonMain {
      kotlin.srcDir(generateVersionKtTask)

      dependencies {
        // Compose
        api(compose.runtime)

        // Logging
        implementation("org.jraf:klibnanolog:_")

        // Kotlinx IO
        api("org.jetbrains.kotlinx:kotlinx-io-core:_")
      }
    }

    jvmMain {
      dependencies {
        // Not sure why this is needed, but Compose JVM whines without it
        implementation(AndroidX.collection)
      }
    }
  }
}

publishing {
  repositories {
    maven {
      // Note: declare your user name / password in your home's gradle.properties like this:
      // mavenCentralNexusUsername = <user name>
      // mavenCentralNexusPassword = <password>
      url = uri("https://oss.sonatype.org/service/local/staging/deploy/maven2")
      name = "mavenCentralNexus"
      credentials(PasswordCredentials::class)
    }
  }

  publications.withType<MavenPublication>().forEach { publication ->
    publication.artifact(dokkaHtmlJar)

    publication.pom {
      name.set("k2o")
      description.set("An OpenSCAD Kotlin DSL")
      url.set("https://github.com/BoD/k2o")
      licenses {
        license {
          name.set("Gnu General Public License version 3")
          url.set("https://www.gnu.org/licenses/gpl-3.0.html")
          distribution.set("repo")
        }
      }
      developers {
        developer {
          id.set("BoD")
          name.set("Benoit 'BoD' Lubek")
          email.set("BoD@JRAF.org")
          url.set("https://JRAF.org")
          organization.set("JRAF.org")
          organizationUrl.set("https://JRAF.org")
          roles.set(listOf("developer"))
          timezone.set("+1")
        }
      }
      scm {
        connection.set("scm:git:https://github.com/BoD/k2o")
        developerConnection.set("scm:git:https://github.com/BoD/k2o")
        url.set("https://github.com/BoD/k2o")
      }
      issueManagement {
        url.set("https://github.com/BoD/k2o/issues")
        system.set("GitHub Issues")
      }
    }
  }
}

val doSign = !project.findProperty("signing.keyId")?.toString().isNullOrBlank()
signing {
  // Note: declare the signature key, password and file in your home's gradle.properties like this:
  // signing.keyId=<8 character key>
  // signing.password=<your password>
  // signing.secretKeyRingFile=<absolute path to the gpg private key>
  if (doSign) {
    sign(publishing.publications)
  }
}

// Workaround for https://youtrack.jetbrains.com/issue/KT-46466
val dependsOnTasks = mutableListOf<String>()
tasks.withType<AbstractPublishToMaven>().configureEach {
  if (doSign) {
    dependsOnTasks.add(this.name.replace("publish", "sign").replaceAfter("Publication", ""))
    dependsOn(dependsOnTasks)
  }
}

dokka {
  dokkaPublications.html {
    outputDirectory.set(rootProject.file("docs"))
  }
}

// Run `./gradlew dokkaHtml` to generate the docs
// Run `./gradlew publishToMavenLocal` to publish to the local maven repo
// Run `./gradlew publish` to publish to Maven Central (then go to https://oss.sonatype.org/#stagingRepositories and "close", and "release")
