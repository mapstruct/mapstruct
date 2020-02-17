# MapStruct - Java bean mappings, the easy way!

[![Latest Stable Version](https://img.shields.io/badge/Latest%20Stable%20Version-1.3.1.Final-blue.svg)](http://search.maven.org/#search%7Cga%7C1%7Cg%3Aorg.mapstruct%20AND%20v%3A1.*.Final)
[![Latest Version](https://img.shields.io/maven-central/v/org.mapstruct/mapstruct-processor.svg?maxAge=3600&label=Latest%20Release)](http://search.maven.org/#search%7Cga%7C1%7Cg%3Aorg.mapstruct)
[![License](https://img.shields.io/badge/License-Apache%202.0-yellowgreen.svg)](https://github.com/mapstruct/mapstruct/blob/master/LICENSE.txt)

[![Build Status](https://img.shields.io/travis/mapstruct/mapstruct.svg)](https://travis-ci.org/mapstruct/mapstruct)
[![Coverage Status](https://img.shields.io/codecov/c/github/mapstruct/mapstruct.svg)](https://codecov.io/gh/mapstruct/mapstruct)
[![Gitter](https://img.shields.io/gitter/room/mapstruct/mapstruct.svg)](https://gitter.im/mapstruct/mapstruct-users)
[![Code Quality: Java](https://img.shields.io/lgtm/grade/java/g/mapstruct/mapstruct.svg?logo=lgtm&logoWidth=18)](https://lgtm.com/projects/g/mapstruct/mapstruct/context:java)
[![Total Alerts](https://img.shields.io/lgtm/alerts/g/mapstruct/mapstruct.svg?logo=lgtm&logoWidth=18)](https://lgtm.com/projects/g/mapstruct/mapstruct/alerts)

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

MapStruct is a Java [annotation processor](http://docs.oracle.com/javase/6/docs/technotes/guides/apt/index.html) for the generation of type-safe and performant mappers for Java bean classes. It saves you from writing mapping code by hand, which is a tedious and error-prone task. The generator comes with sensible defaults and many built-in type conversions, but it steps out of your way when it comes to configuring or implementing special behavior.

Compared to mapping frameworks working at runtime, MapStruct offers the following advantages:

* **Fast execution** by using plain method invocations instead of reflection
* **Compile-time type safety**. Only objects and attributes mapping to each other can be mapped, so there's no accidental mapping of an order entity into a customer DTO, etc.
* **Self-contained code**—no runtime dependencies
* **Clear error reports** at build time if:
  * mappings are incomplete (not all target properties are mapped)
  * mappings are incorrect (cannot find a proper mapping method or type conversion)
* **Easily debuggable mapping code** (or editable by hand—e.g. in case of a bug in the generator)

To create a mapping between two types, declare a mapper class like this:

```java
@Mapper
public interface CarMapper {

    CarMapper INSTANCE = Mappers.getMapper( CarMapper.class );

    @Mapping(source = "numberOfSeats", target = "seatCount")
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
    <org.mapstruct.version>1.3.1.Final</org.mapstruct.version>
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
            <version>3.8.1</version>
            <configuration>
                <source>1.8</source>
                <target>1.8</target>
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
    id 'net.ltgt.apt' version '0.15'
}

// You can integrate with your IDEs.
// See more details: https://github.com/tbroyer/gradle-apt-plugin#usage-with-ides
apply plugin: 'net.ltgt.apt-idea'
apply plugin: 'net.ltgt.apt-eclipse'

dependencies {
    ...
    compile 'org.mapstruct:mapstruct:1.3.1.Final'

    annotationProcessor 'org.mapstruct:mapstruct-processor:1.3.1.Final'
    testAnnotationProcessor 'org.mapstruct:mapstruct-processor:1.3.1.Final' // if you are using mapstruct in test code
}
...
```

If you don't work with a dependency management tool, you can obtain a distribution bundle from [SourceForge](https://sourceforge.net/projects/mapstruct/files/).

## Documentation and getting help

To learn more about MapStruct, refer to the [project homepage](http://mapstruct.org). The [reference documentation](http://mapstruct.org/documentation/reference-guide/) covers all provided functionality in detail. If you need help, come and join the [mapstruct-users](https://groups.google.com/forum/?hl=en#!forum/mapstruct-users) group.

## Building from Source

MapStruct uses Maven for its build. Java 8 is required for building MapStruct from source. To build the complete project, run

    mvn clean install

from the root of the project directory. To skip the distribution module, run 

    mvn clean install -DskipDistribution=true
    
## Importing into IDE

MapStruct uses the gem annotation processor to generate mapping gems for it's own annotations.
Therefore for seamless integration within an IDE annotation processing needs to be enabled.

### IntelliJ 

Make sure that you have at least IntelliJ 2018.2.x (needed since support for `annotationProcessors` from the `maven-compiler-plugin` is from that version).
Enable annotation processing in IntelliJ (Build, Execution, Deployment -> Compiler -> Annotation Processors)

### Eclipse

Make sure that you have the [m2e_apt](https://marketplace.eclipse.org/content/m2e-apt) plugin installed.

## Links

* [Homepage](http://mapstruct.org)
* [Source code](https://github.com/mapstruct/mapstruct/)
* [Downloads](https://sourceforge.net/projects/mapstruct/files/)
* [Issue tracker](https://github.com/mapstruct/mapstruct/issues)
* [User group](https://groups.google.com/forum/?hl=en#!forum/mapstruct-users)
* [CI build](https://travis-ci.org/mapstruct/mapstruct/)

## Licensing

MapStruct is licensed under the Apache License, Version 2.0 (the "License"); you may not use this project except in compliance with the License. You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0.
