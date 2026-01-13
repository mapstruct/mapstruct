# KSP Processor Tests

This directory contains tests for the MapStruct KSP (Kotlin Symbol Processing) processor.

## Quick Start

### Writing a Test

1. Create test class in appropriate package (e.g., `simple/`, `collection/`, `builder/`)
2. Create corresponding source files in `src/test/resources` with same package structure
3. Annotate test method with `@KspTest` and `@WithSources`

**Example:**

```kotlin
// src/test/kotlin/org/mapstruct/ksp/test/simple/SimpleMapperTest.kt
class SimpleMapperTest {
    @KspTest
    @WithSources("Source.kt", "Target.kt", "SimpleMapper.kt")
    fun shouldGenerateSimpleMapper() {
        // Test passes if compilation succeeds
    }
}
```

### Running Tests

```bash
# All tests
./mvnw test -pl ksp-processor

# Specific test
./mvnw test -pl ksp-processor -Dtest=SimpleMapperTest
```

## Test Structure

```
test/
├── kotlin/org/mapstruct/ksp/test/
│   ├── testutil/              # Test framework infrastructure
│   │   ├── KspTest.kt        # Main test annotation
│   │   ├── KspTestExtension.kt  # JUnit extension
│   │   ├── WithSources.kt    # Source file specification
│   │   └── ...
│   ├── simple/               # Basic mapping tests
│   ├── collection/           # Collection mapping tests
│   ├── erroneous/            # Error handling tests
│   └── ...
└── resources/org/mapstruct/ksp/test/
    ├── simple/               # Source files for simple tests
    │   ├── Source.kt
    │   ├── Target.kt
    │   └── SimpleMapper.kt
    └── ...
```

## Test Framework Features

### Annotations

- **`@KspTest`** - Marks test method (like processor's `@ProcessorTest`)
- **`@WithSources`** - Specifies source files to compile
- **`@KspProcessorOption`** - Passes processor options
- **`@ExpectedCompilationOutcome`** - Validates compilation result and errors

### Comparison with Processor Module

| Feature | Processor | KSP Processor |
|---------|-----------|---------------|
| Annotation | `@ProcessorTest` | `@KspTest` |
| Sources | `@WithClasses` (Java classes) | `@WithSources` (Kotlin files) |
| Location | Java source tree | Test resources |
| Compilers | JDK + Eclipse | kotlin-compile-testing + KSP |

## Test Categories

Organize tests to mirror processor module structure:

- **simple/** - Basic property mapping
- **collection/** - List, Set, Map mappings
- **builder/** - Builder pattern support
- **inheritance/** - Interface and class inheritance
- **erroneous/** - Error detection and reporting
- **bugs/** - Bug regression tests

## Examples

### Success Test
```kotlin
@KspTest
@WithSources("Source.kt", "Target.kt", "Mapper.kt")
fun shouldMapProperties() {
    // Validates successful compilation
}
```

### Failure Test
```kotlin
@KspTest
@WithSources("InvalidMapper.kt")
@ExpectedCompilationOutcome(
    value = CompilationResult.FAILED,
    diagnostics = [Diagnostic(kind = DiagnosticKind.ERROR, messageRegex = ".*error.*")]
)
fun shouldFailOnInvalidMapping() {
    // Validates expected compilation failure
}
```

### With Options
```kotlin
@KspTest
@WithSources("Mapper.kt")
@KspProcessorOption(name = "mapstruct.verbose", value = "true")
fun shouldGenerateWithVerboseOutput() {
    // Compilation includes processor option
}
```

## Documentation

See [TESTING.md](../../../TESTING.md) for complete testing guide.

## Contributing

When adding tests:

1. Follow existing package structure
2. Place source files in `src/test/resources` matching package
3. Use descriptive test and file names
4. Add `@ExpectedCompilationOutcome` for error tests
5. Document complex test scenarios with comments
