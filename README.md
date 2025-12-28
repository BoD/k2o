[![Maven Central](https://img.shields.io/maven-metadata/v?metadataUrl=https%3A%2F%2Frepo1.maven.org%2Fmaven2%2Forg%2Fjraf%2Fk2o%2Fk2o%2Fmaven-metadata.xml&style=flat-square&label=maven-central&strategy=latestProperty)](https://central.sonatype.com/namespace/org.jraf.k2o) [![Maven Snapshots](https://img.shields.io/maven-metadata/v?metadataUrl=https%3A%2F%2Fcentral.sonatype.com%2Frepository%2Fmaven-snapshots%2Forg%2Fjraf%2Fk2o%2Fk2o%2Fmaven-metadata.xml&style=flat-square&label=snapshots&color=%2315252D&strategy=latestProperty)](https://central.sonatype.com/repository/maven-snapshots/org/jraf/k2o)

# k2o

K2o or "Kotlin to OpenSCAD" is a Kotlin DSL to generate OpenSCAD code.

## The idea

While [OpenSCAD](https://openscad.org/) is truly great in general, and its language is actually quite good (and already pretty similar to
Kotlin!), the more I've been using it, the more I wished I could just "do OpenSCAD in Kotlin".

This would bring a few advantages:

- All the benefits of a modern language with functions, classes, packages, etc. and a proper type system
- Great IDE support (autocompletion, refactoring, etc.)
- Benefit from a huge ecosystem of Kotlin (and Java!) libraries
- The ability to share libraries on Maven Central or other repositories

This library is an attempt to bring that to life by providing an OpenSCAD DSL: write Kotlin code which when run outputs OpenSCAD code. You
can then open the result in OpenSCAD's UI or just run the OpenSCAD CLI to convert it to stl.

## Usage

Add the dependency to your `build.gradle.kts`:

```kotlin
implementation("org.jraf.k2o:k2o:$latestVersion")
```

Then call standard OpenSCAD functions (or your own) inside the `openScad { }` block:

```kotlin
fun main() {
  openScad {
    CubeMinusSphere()
  }
}

@Composable
private fun CubeMinusSphere() {
  difference {
    Cube(size = 10)
    translate(x = 2.5, y = 2.5, z = 2.5) {
      Sphere(radius = 5)
    }
  }
}
```

This will output:

```openscad
$fa = 0.1;
$fs = 0.1;

difference() {
  cube(10);
  translate([2.5, 2.5, 2.5]) {
    sphere(r = 5);
  }
}
```

As you can see, the DSL is very close to OpenSCAD itself so you should feel right at home.

## FAQ

### `@Composable`?

While this doesn't really use anything from Jetpack Compose, I found the `@Composable` annotation is a good fit for this kind of DSL where
functions represent UI elements (3D elements in this case) that are rendered together.
It is also a nice way to have the context around without having to pass it explicitly, and to force the use of the DSL only inside the
`openScad` block or other composable functions.

## Author and license

```
Copyright (C) 2025-present Benoit 'BoD' Lubek (BoD@JRAF.org).

Licensed under the Apache License, Version 2.0.
```
