# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## About MapStruct

MapStruct is a code generator that generates type-safe, high-performance mapper implementations for Java and Kotlin bean mappings at compile-time. It provides both a Java annotation processor and a Kotlin Symbol Processing (KSP) plugin. The processors analyze `@Mapper` annotated interfaces and generate implementation code using FreeMarker templates.

## Build Requirements

- **Java 21** is required to build MapStruct from source
- MapStruct itself requires **Java 1.8** or later at runtime
- Maven is used for the build system (use the included Maven wrapper `./mvnw`)

## Essential Build Commands

### Full Build
```bash
./mvnw clean install
```

### Skip Distribution Module
```bash
./mvnw clean install -DskipDistribution=true
```

### Run Tests in Specific Module
```bash
./mvnw clean test -pl processor
./mvnw clean test -pl ksp-processor  # For KSP processor tests
```

### Run a Single Test
```bash
./mvnw test -pl processor -Dtest=Issue631Test
./mvnw test -pl ksp-processor -Dtest=MapStructSymbolProcessorTest  # For KSP tests
```

### Run Checkstyle
```bash
./mvnw checkstyle:check
```

### Generate Code Coverage Report
```bash
./mvnw clean install
# Reports available in processor/target/site/jacoco/
```

## Project Structure

### Core Modules

- **`core/`** - Contains the MapStruct API annotations (`@Mapper`, `@Mapping`, etc.) that users write in their code. This is the compile-time dependency users add to their projects.

- **`core-jdk8/`** - JDK8-specific extensions to the core API.

- **`processor/`** - The annotation processor that generates mapper implementations. This is where the code generation logic lives.
  - `src/main/java/org/mapstruct/ap/` - Main processor code
    - `MappingProcessor.java` - Entry point for the annotation processor
    - `spi/` - Service Provider Interfaces for extensibility
    - `internal/processor/` - Core processing logic
    - `internal/model/` - Model classes representing mapper structure
    - `internal/writer/` - Code generation using FreeMarker templates
    - `internal/conversion/` - Built-in type conversions
    - `internal/gem/` - Generated "gems" for reading annotations without class dependencies
    - `internal/util/` - Utility classes
  - `src/main/resources/` - FreeMarker templates for code generation
  - `src/test/java/org/mapstruct/ap/test/` - Comprehensive test suite organized by feature
    - Tests use the `@ProcessorTest` annotation which compiles test mappers using both JDK and Eclipse compilers
    - Test structure: Each test typically has mapper interfaces and source/target classes, then verifies generated code

- **`ksp-processor/`** - Kotlin Symbol Processing (KSP) implementation of the MapStruct processor for native Kotlin support.
  - `src/main/kotlin/org/mapstruct/ksp/` - Main KSP processor code
    - `MapStructSymbolProcessorProvider.kt` - Entry point for KSP processor registration
    - `MapStructSymbolProcessor.kt` - Main processor that discovers and processes @Mapper annotations
    - `adapter/` - Adapter layer bridging KSP and Java annotation processing APIs
    - `internal/processor/` - KSP-specific processing logic
    - `util/` - Conversion utilities between KSP symbols and Java model elements
  - `src/test/kotlin/org/mapstruct/ksp/test/` - KSP processor test suite
  - See `ksp-processor/README.md` for detailed documentation

- **`integrationtest/`** - Integration tests that verify MapStruct works in real project scenarios

- **`documentation/`** - Reference documentation and user guides

- **`distribution/`** - Distribution packaging

### Key Technologies

- **Gem Tools** - Used to generate type-safe "gem" classes for reading annotation attributes without depending on annotation class objects
- **FreeMarker** - Template engine used to generate Java source code from the mapper model
- **ServiceLoader** - Used to load `ModelElementProcessor` implementations in priority order for processing mapper interfaces
- **KSP (Kotlin Symbol Processing)** - Modern API for Kotlin code generation with better performance and incremental compilation support

## Testing

### Writing Tests

Tests are located in `processor/src/test/java/org/mapstruct/ap/test/`. Tests are organized by feature area (e.g., `bugs/`, `builder/`, `collection/`, `inheritance/`).

Standard test structure:
1. Annotate test methods with `@ProcessorTest` (runs with JDK and Eclipse compilers)
2. Use `@WithClasses` to specify mapper interfaces and classes to compile
3. Use `@ExpectedCompilationOutcome` for tests that expect compilation errors
4. Use `@ProcessorOption` to pass options to the annotation processor
5. Verify the generated mapper works correctly by invoking its methods

Example test:
```java
@ProcessorTest
@WithClasses({ SourceClass.java, TargetClass.java, MyMapper.java })
public void shouldGenerateCorrectMapping() {
    SourceClass source = new SourceClass();
    source.setName("test");

    TargetClass target = MyMapper.INSTANCE.map(source);

    assertThat(target.getName()).isEqualTo("test");
}
```

### Running Tests

Individual test classes can be run using:
```bash
./mvnw test -pl processor -Dtest=ClassName
```

Test methods within a class:
```bash
./mvnw test -pl processor -Dtest=ClassName#methodName
```

## Code Style

The project uses a custom code formatter configuration at `etc/mapstruct.xml`. IntelliJ IDEA users should import this formatter configuration.

To check code style compliance:
```bash
./mvnw checkstyle:check
```

## IDE Setup

### IntelliJ IDEA

1. **Requires IntelliJ 2018.2.x or newer** (for `annotationProcessors` support from `maven-compiler-plugin`)
2. Enable annotation processing: Build, Execution, Deployment → Compiler → Annotation Processors
3. Import code formatter: Import `etc/mapstruct.xml` as IntelliJ formatter settings
4. MapStruct uses the gem annotation processor to generate mapping gems for its own annotations, so annotation processing must be enabled for seamless IDE integration

### Eclipse

1. Install the [m2e_apt](https://marketplace.eclipse.org/content/m2e-apt) plugin for annotation processing support

## Processor Architecture

The MapStruct processor follows a multi-phase approach:

1. **Element Discovery** - Find all `@Mapper` annotated interfaces
2. **Model Building** - Create internal `Mapper` model objects representing each mapper to generate
3. **Model Processing** - Apply a sequence of `ModelElementProcessor` implementations (loaded via ServiceLoader) in priority order:
   - Retrieve mapping methods
   - Process annotations and configuration
   - Generate property mappings
   - Add dependency injection annotations if configured
   - Apply enrichments and modifications
4. **Code Generation** - If no errors occurred, write model to Java source files using FreeMarker templates

Each element in the model hierarchy has a corresponding FreeMarker template that recursively includes sub-elements.

## Common Development Patterns

### Adding a New Feature

1. Add annotations to `core/src/main/java/org/mapstruct/` if needed
2. Implement processing logic in `processor/src/main/java/org/mapstruct/ap/internal/`
3. Add or update FreeMarker templates in `processor/src/main/resources/` if code generation changes
4. Write comprehensive tests in `processor/src/test/java/org/mapstruct/ap/test/`
5. Update reference documentation in `documentation/`

### Adding a New Built-in Type Conversion

Add conversion logic in `processor/src/main/java/org/mapstruct/ap/internal/conversion/` and corresponding tests.

### Extending via SPI

MapStruct supports extension through SPIs in `processor/src/main/java/org/mapstruct/ap/spi/`:
- `AccessorNamingStrategy` - Customize getter/setter recognition
- `BuilderProvider` - Support for custom builder patterns
- `EnumMappingStrategy` - Customize enum mapping behavior
- `MappingExclusionProvider` - Exclude certain mappings

## Debugging Generated Code

Generated mapper implementations are written to `target/generated-sources/annotations/`. You can examine these files to understand what MapStruct generated and debug any issues.
