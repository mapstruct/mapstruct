# MapStruct KSP Processor

This module provides a Kotlin Symbol Processing (KSP) implementation of MapStruct's mapper generation, enabling native support for Kotlin projects.

## Overview

The KSP processor offers the same functionality as the Java annotation processor but is optimized for Kotlin code. It processes `@Mapper` annotated interfaces and abstract classes to generate type-safe mapper implementations at compile time.

## Architecture

### Core Components

- **`MapStructSymbolProcessorProvider`** - Entry point for KSP, creates processor instances
- **`MapStructSymbolProcessor`** - Main processor that orchestrates mapper generation
- **Adapter Layer** (`org.mapstruct.ksp.adapter`) - Bridges KSP and Java annotation processing APIs
  - `KspProcessingEnvironmentAdapter` - Adapts `SymbolProcessorEnvironment` to `ProcessingEnvironment`
  - `KspMessagerAdapter` - Routes diagnostics between KSP logger and `Messager`
  - `KspFilerAdapter` - Adapts `CodeGenerator` to `Filer` for file creation
  - `KspElementsAdapter` - Provides element utilities
  - `KspTypesAdapter` - Provides type utilities

### Design Approach

The KSP processor reuses the existing MapStruct model and processing infrastructure through an adapter layer. This approach:

1. **Maximizes code reuse** - Leverages existing tested logic from the Java annotation processor
2. **Maintains feature parity** - Same capabilities as the Java processor
3. **Simplifies maintenance** - Core processing logic is shared between both processors

### Processing Pipeline

The processor follows the same multi-phase approach as the Java annotation processor:

1. **Element Discovery** - Find all `@Mapper` annotated types using `Resolver.getSymbolsWithAnnotation()`
2. **Model Building** - Convert KSP symbols to MapStruct's internal model representation
3. **Model Processing** - Apply the existing chain of `ModelElementProcessor` implementations
4. **Code Generation** - Generate Java/Kotlin source files using KSP's `CodeGenerator`

### Deferred Processing

The processor handles incomplete type hierarchies by deferring mappers to subsequent processing rounds, similar to the Java annotation processor's approach.

## Usage

### Gradle (Kotlin DSL)

```kotlin
plugins {
    kotlin("jvm") version "2.0.21"
    id("com.google.devtools.ksp") version "2.0.21-1.0.25"
}

dependencies {
    implementation("org.mapstruct:mapstruct:1.7.0-SNAPSHOT")
    ksp("org.mapstruct:mapstruct-ksp-processor:1.7.0-SNAPSHOT")
}
```

### Gradle (Groovy)

```groovy
plugins {
    id 'org.jetbrains.kotlin.jvm' version '2.0.21'
    id 'com.google.devtools.ksp' version '2.0.21-1.0.25'
}

dependencies {
    implementation 'org.mapstruct:mapstruct:1.7.0-SNAPSHOT'
    ksp 'org.mapstruct:mapstruct-ksp-processor:1.7.0-SNAPSHOT'
}
```

### Configuration Options

The processor supports the same configuration options as the Java annotation processor:

```kotlin
ksp {
    arg("mapstruct.defaultComponentModel", "spring")
    arg("mapstruct.unmappedTargetPolicy", "ERROR")
    arg("mapstruct.verbose", "true")
}
```

## Kotlin-Specific Features

### Nullable Types

The processor understands Kotlin's nullable types:

```kotlin
@Mapper
interface PersonMapper {
    fun toDto(person: Person): PersonDto  // Non-null mapping
    fun toDtoOrNull(person: Person?): PersonDto?  // Nullable mapping
}
```

### Data Classes

Kotlin data classes are fully supported as source and target types:

```kotlin
data class Person(val name: String, val age: Int)
data class PersonDto(val name: String, val age: Int)

@Mapper
interface PersonMapper {
    fun toDto(person: Person): PersonDto
}
```

### Default Parameters

Methods with default parameters are supported:

```kotlin
@Mapper
interface PersonMapper {
    fun toDto(person: Person, includeDetails: Boolean = true): PersonDto
}
```

### Companion Objects

Mapper instances can be accessed via companion objects:

```kotlin
@Mapper
interface PersonMapper {
    fun toDto(person: Person): PersonDto

    companion object {
        val INSTANCE: PersonMapper = PersonMapperImpl()
    }
}
```

## Current Status

**⚠️ Work in Progress**

This module is under active development. The following components are complete:

- ✅ Module structure and build configuration
- ✅ Core KSP processor implementation (skeleton)
- ✅ Adapter layer between KSP and Java annotation processing APIs
- ✅ Service provider registration

The following components are still in development:

- 🚧 Element discovery and model building
- 🚧 Code generation integration
- 🚧 Kotlin-specific feature handling
- 🚧 Testing infrastructure
- 🚧 Full integration with existing MapStruct model

## Development

### Building

```bash
./mvnw clean install -pl ksp-processor
```

### Running Tests

```bash
./mvnw test -pl ksp-processor
```

## Differences from Java Annotation Processor

| Aspect | Java AP | KSP Processor |
|--------|---------|---------------|
| Type System | `javax.lang.model.*` | KSP symbols via adapter |
| File Generation | `Filer` | `CodeGenerator` via adapter |
| Diagnostics | `Messager` | `KSPLogger` via adapter |
| Performance | Standard | ~2x faster (KSP benefit) |
| Incremental Compilation | Limited | Full support (KSP benefit) |
| Kotlin Features | Limited | Native support |

## Contributing

When contributing to the KSP processor:

1. Maintain compatibility with existing MapStruct features
2. Reuse existing model and processing logic where possible
3. Add Kotlin-specific enhancements where appropriate
4. Update tests to cover both Java AP and KSP processor
5. Document Kotlin-specific behavior

## Resources

- [KSP Documentation](https://kotlinlang.org/docs/ksp-overview.html)
- [MapStruct Documentation](https://mapstruct.org/)
- [MapStruct GitHub](https://github.com/mapstruct/mapstruct)
