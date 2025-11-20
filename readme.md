# MapStruct - Java bean mappings, the easy way!

[![Latest Stable Version](https://img.shields.io/badge/Latest%20Stable%20Version-1.6.3-blue.svg)](https://central.sonatype.com/search?q=g:org.mapstruct%20v:1.6.3)
[![Latest Version](https://img.shields.io/maven-central/v/org.mapstruct/mapstruct-processor.svg?maxAge=3600&label=Latest%20Release)](https://central.sonatype.com/search?q=g:org.mapstruct)
[![License](https://img.shields.io/badge/License-Apache%202.0-yellowgreen.svg)](https://github.com/mapstruct/mapstruct/blob/main/LICENSE.txt)

[![Build Status](https://github.com/mapstruct/mapstruct/workflows/CI/badge.svg?branch=main)](https://github.com/mapstruct/mapstruct/actions?query=branch%3Amain+workflow%3ACI)
[![Coverage Status](https://img.shields.io/codecov/c/github/mapstruct/mapstruct.svg)](https://codecov.io/gh/mapstruct/mapstruct/tree/main)

* [What is MapStruct?](#what-is-mapstruct)
* [Requirements](#requirements)
* [Using MapStruct](#using-mapstruct)
 * [Maven](#maven)
 * [Gradle](#gradle)
* [Documentation and getting help](#documentation-and-getting-help)
* [Building from Source](#building-from-source)
* [Links](#links)
* [Licensing](#licensing)

## What is MapStruct?

MapStruct is a Java [annotation processor](https://docs.oracle.com/en/java/javase/21/docs/specs/man/javac.html#annotation-processing) designed to generate type-safe and high-performance mappers for Java bean classes, including support for Java 16+ records.
By automating the creation of mappings, MapStruct eliminates the need for tedious and error-prone manual coding. 
The generator provides sensible defaults and built-in type conversions, allowing it to handle standard mappings effortlessly, while also offering flexibility for custom configurations or specialized mapping behaviors.
With seamless integration into modern Java projects, MapStruct can map between conventional beans, records, and even complex hierarchies, making it an adaptable tool for diverse Java applications.

Compared to mapping frameworks working at runtime, MapStruct offers the following advantages:

* **Fast execution** by using plain method invocations instead of reflection
* **Compile-time type safety**. Only objects and attributes mapping to each other can be mapped, so there's no accidental mapping of an order entity into a customer DTO, etc.
* **Self-contained code**—no runtime dependencies
* **Clear error reports** at build time if:
  * mappings are incomplete (not all target properties are mapped)
  * mappings are incorrect (cannot find a proper mapping method or type conversion)
* **Easily debuggable mapping code** (or editable by hand—e.g. in case of a bug in the generator)

To create a mapping between two types, declare a mapper interface like this:

```java
@Mapper
public interface CarMapper {

    CarMapper INSTANCE = Mappers.getMapper( CarMapper.class );

    @Mapping(target = "seatCount", source = "numberOfSeats")
    CarDto carToCarDto(Car car);
}
```

At compile time MapStruct will generate an implementation of this interface. The generated implementation uses plain Java method invocations for mapping between source and target objects, i.e. no reflection is involved. By default, properties are mapped if they have the same name in source and target, but you can control this and many other aspects using `@Mapping` and a handful of other annotations.

## Requirements

MapStruct requires Java 1.8 or later.

## Using MapStruct

MapStruct works in command line builds (plain javac, via Maven, Gradle, Ant, etc.) and IDEs.

For Eclipse, a dedicated plug-in is in development (see https://github.com/mapstruct/mapstruct-eclipse). It goes beyond what's possible with an annotation processor, providing content assist for annotation attributes, quick fixes and more.

For IntelliJ the plug-in is available within the IntelliJ marketplace (see https://plugins.jetbrains.com/plugin/10036-mapstruct-support).

### Maven

For Maven-based projects, add the following to your POM file in order to use MapStruct (the dependencies are available at Maven Central):

```xml
...
<properties>
    <org.mapstruct.version>1.6.3</org.mapstruct.version>
</properties>
...
<dependencies>
    <dependency>
        <groupId>org.mapstruct</groupId>
        <artifactId>mapstruct</artifactId>
        <version>${org.mapstruct.version}</version>
    </dependency>
</dependencies>
...
<build>
    <plugins>
        <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-compiler-plugin</artifactId>
            <version>3.13.0</version>
            <configuration>
                <source>17</source>
                <target>17</target>
                <annotationProcessorPaths>
                    <path>
                        <groupId>org.mapstruct</groupId>
                        <artifactId>mapstruct-processor</artifactId>
                        <version>${org.mapstruct.version}</version>
                    </path>
                </annotationProcessorPaths>
            </configuration>
        </plugin>
    </plugins>
</build>
...
```

### Gradle

For Gradle, you need something along the following lines:

```groovy
plugins {
    ...
    id "com.diffplug.eclipse.apt" version "3.26.0" // Only for Eclipse
}

dependencies {
    ...
    implementation 'org.mapstruct:mapstruct:1.6.3'

    annotationProcessor 'org.mapstruct:mapstruct-processor:1.6.3'
    testAnnotationProcessor 'org.mapstruct:mapstruct-processor:1.6.3' // if you are using mapstruct in test code
}
...
```

If you don't work with a dependency management tool, you can obtain a distribution bundle from [Releases page](https://github.com/mapstruct/mapstruct/releases).

## Documentation and getting help

To learn more about MapStruct, refer to the [project homepage](https://mapstruct.org). The [reference documentation](https://mapstruct.org/documentation/reference-guide/) covers all provided functionality in detail. If you need help please ask it in the [Discussions](https://github.com/mapstruct/mapstruct/discussions).

## Building from Source

MapStruct uses Maven for its build. Java 21 is required for building MapStruct from source.
To build the complete project, run

    ./mvnw clean install

from the root of the project directory. To skip the distribution module, run 

    ./mvnw clean install -DskipDistribution=true
    
## Importing into IDE

MapStruct uses the gem annotation processor to generate mapping gems for its own annotations.
Therefore, for seamless integration within an IDE annotation processing needs to be enabled.

### IntelliJ 

Make sure that you have at least IntelliJ 2018.2.x (needed since support for `annotationProcessors` from the `maven-compiler-plugin` is from that version).
Enable annotation processing in IntelliJ (Build, Execution, Deployment -> Compiler -> Annotation Processors)

### Eclipse

Make sure that you have the [m2e_apt](https://marketplace.eclipse.org/content/m2e-apt) plugin installed.

## Links

* [Homepage](https://mapstruct.org)
* [Source code](https://github.com/mapstruct/mapstruct/)
* [Downloads](https://github.com/mapstruct/mapstruct/releases)
* [Issue tracker](https://github.com/mapstruct/mapstruct/issues)
* [User group](https://groups.google.com/forum/?hl=en#!forum/mapstruct-users)
* [CI build](https://github.com/mapstruct/mapstruct/actions/)

## Licensing

MapStruct is licensed under the Apache License, Version 2.0 (the "License"); you may not use this project except in compliance with the License. You may obtain a copy of the License at https://www.apache.org/licenses/LICENSE-2.0.
