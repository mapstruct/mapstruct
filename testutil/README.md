# JBarista - Annotation Processor Testing the easy way!

* [What is JBarista?](#what-is-jbarista)
* [Requirements](#requirements)
* [Using JBarista](#using-jbarista)
 * [Maven](#maven)
* [Licensing](#licensing)

## What is JBarista?

JBarista is a java [annotation processor](http://docs.oracle.com/javase/6/docs/technotes/guides/apt/index.html) testing framework. Allows for testing annotation processors with both the `jdk` and `eclipse` compilers.

The testing framework allows for easy definable tests:

```java
@WithClasses( { MyAnnotatedClass.class } )
class AnnotationProcessorTest {

    @ProcessorTest
    void compilationSuccessfulTest() {
    }

    @ProcessorTest
    @ExpectedCompilationOutcome(value = CompilationResult.FAILED, diagnostics = {
        @Diagnostic(
            type = MyAnnotatedClass.class,
            kind = javax.tools.Diagnostic.Kind.ERROR,
            line = 17,
            message = "My custom compiler error message or java compiler error message."
        )
    })
    void compilationFailureTest() {
    }
```

## Requirements

JBarista requires Java 1.8 or later.

## Using JBarista

JBarista uses the JUnit 5 and AssertJ testing frameworks as a basis.

### Maven

For Maven-based projects, add the following to your POM file in order to use JBarista (the dependencies are available at Maven Central):

```xml
...
<properties>
    <org.jbarista.version>1.0.0</org.jbarista.version>
</properties>
...
<dependencies>
    <dependency>
        <groupId>org.jbarista</groupId>
        <artifactId>jbarista</artifactId>
        <version>${org.jbarista.version}</version>
        <scope>test</scope>
    </dependency>
</dependencies>
...
```

## Licensing

JBarista is licensed under the Apache License, Version 2.0 (the "License"); you may not use this project except in compliance with the License. You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0.