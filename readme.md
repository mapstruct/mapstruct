# MapStruct - Java bean mappings, the easy way!

_Version: 1.0.0.CR2, August 27th 2015_

## What is MapStruct?

MapStruct is a Java [annotation processor](http://docs.oracle.com/javase/6/docs/technotes/guides/apt/index.html) for the generation of type-safe bean mapping classes.

All you have to do is to define a mapper interface which declares any required mapping methods. During compilation, MapStruct will generate an implementation of this interface. This implementation uses plain Java method invocations for mapping between source and target objects, i.e. no reflection or similar.

Compared to writing mapping code from hand, MapStruct saves time by generating code which is tedious and error-prone to write. Following a convention over configuration approach, MapStruct uses sensible defaults but steps out of your way when it comes to configuring or implementing special behavior.

Compared to dynamic mapping frameworks, MapStruct offers the following advantages:

* Fast execution by using plain method invocations instead of reflection
* Compile-time type safety: Only objects and attributes mapping to each other can be mapped, no accidental mapping of an order entity into a customer DTO etc.
* Self-contained code, no runtime dependencies
* Clear error-reports at build time, if entities or attributes can't be mapped

## Documentation and getting help

To learn more about MapStruct in two minutes, refer to the [project homepage](http://mapstruct.org). The [reference documentation](http://mapstruct.org/documentation) covers all provided functionality in detail. If you need help, come and join the [mapstruct-users](https://groups.google.com/forum/?hl=en#!forum/mapstruct-users) group.

## Requirements

MapStruct requires Java 1.6 or later.

## Using MapStruct

MapStruct is a Java annotation processor based on [JSR 269](jcp.org/en/jsr/detail?id=269) and as such can be used within command line builds (javac, Ant, Maven etc.) as well as from within your IDE.

For Maven based projects add the following to your POM file in order to use MapStruct (the dependencies can be obtained from Maven Central):

```xml

...
<properties>
    <org.mapstruct.version>[current MapStruct version]</org.mapstruct.version>
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
            <groupId>org.bsc.maven</groupId>
            <artifactId>maven-processor-plugin</artifactId>
            <version>2.2.4</version>
            <configuration>
                <defaultOutputDirectory>
                    ${project.build.directory}/generated-sources
                </defaultOutputDirectory>
                <processors>
                    <processor>org.mapstruct.ap.MappingProcessor</processor>
                </processors>
            </configuration>
            <executions>
                <execution>
                    <id>process</id>
                    <phase>generate-sources</phase>
                    <goals>
                        <goal>process</goal>
                    </goals>
                </execution>
            </executions>
            <dependencies>
                <dependency>
                    <groupId>org.mapstruct</groupId>
                    <artifactId>mapstruct-processor</artifactId>
                    <version>${org.mapstruct.version}</version>
                </dependency>
            </dependencies>
        </plugin>
    </plugins>
</build>
...
```

Alternatively, a distribution bundle is available from SourceForge.

## Licensing

MapStruct is licensed under the Apache License, Version 2.0 (the "License"); you may not use it except in compliance with the License. You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0.

## Building from Source

MapStruct uses Maven for its build. To build the complete project run

    mvn clean install

from the root of the project directory. To skip the distribution module, run 

    mvn clean install -DskipDistribution=true

## Links

* [Homepage](http://mapstruct.org)
* [Source code](https://github.com/mapstruct/mapstruct/)
* [Downloads](https://sourceforge.net/projects/mapstruct/files/)
* [Issue tracker](https://github.com/mapstruct/mapstruct/issues)
* [User group](https://groups.google.com/forum/?hl=en#!forum/mapstruct-users)
* [CI build](https://mapstruct.ci.cloudbees.com/)

<div style="float: right">
    <a href="https://mapstruct.ci.cloudbees.com/"><img src="http://www.cloudbees.com/sites/default/files/Button-Built-on-CB-1.png"/></a>
</div>
