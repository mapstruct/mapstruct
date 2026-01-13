# MapStruct KSP Processor Sample Project

This is a sample project for debugging the MapStruct KSP processor. It's integrated as a subproject of the `ksp-processor` module to enable debugging without publishing to `mavenLocal`.

## Project Structure

```
ksp-processor/
├── src/                    # KSP processor source code
├── target/
│   └── classes/           # Compiled processor classes
└── sample-project/         # This sample project
    ├── src/
    └── build.gradle.kts   # Depends directly on ../target/classes
```

## How It Works

The sample project uses a Gradle `files()` dependency to reference the compiled ksp-processor classes directly from the Maven build:

```kotlin
dependencies {
    // Use compiled ksp-processor from Maven build
    ksp(files("../target/classes"))
    ksp("org.mapstruct:mapstruct-processor:1.7.0-SNAPSHOT")
}
```

This means:
- ✅ No need to run `mvn install` for ksp-processor
- ✅ Changes to ksp-processor are immediately available
- ✅ Can debug directly in IntelliJ IDEA
- ⚠️ Still need `mvn install` for core and processor (base) modules once

## Building the Sample

### Prerequisites

1. Build the MapStruct core and processor modules (one-time):
   ```bash
   cd /Users/leonid/work/jb/mapstruct
   ./mvnw install -DskipTests -pl core,processor
   ```

2. Build the ksp-processor module:
   ```bash
   ./mvnw clean compile -pl ksp-processor
   ```

### Running the Sample

```bash
cd ksp-processor/sample-project
./gradlew clean build
```

The KSP processor will run against the sample code and generate mapper implementations.

## Debugging with IntelliJ IDEA

### Method 1: Remote Debugging with Gradle

1. **Compile the ksp-processor**:
   ```bash
   cd /Users/leonid/work/jb/mapstruct
   ./mvnw clean compile -pl ksp-processor
   ```

2. **Run Gradle with debug enabled**:
   ```bash
   cd ksp-processor/sample-project
   ./gradlew :kspKotlin --rerun \
     -Dorg.gradle.debug=true \
     --no-daemon \
     -Pkotlin.compiler.execution.strategy=in-process
   ```

3. **Attach IntelliJ debugger**:
   - Run → Attach to Process...
   - Select the Gradle/Kotlin compiler process
   - Set breakpoints in ksp-processor source code
   - Gradle will wait for debugger attachment before starting KSP

### Method 2: Run Configuration in IntelliJ

1. **Create a Gradle Run Configuration**:
   - Run → Edit Configurations → + → Gradle
   - Name: "Debug KSP Sample"
   - Project: mapstruct
   - Tasks: `:ksp-processor:sample-project:kspKotlin --rerun`
   - VM Options:
     ```
     -Dorg.gradle.debug=true
     -Pkotlin.compiler.execution.strategy=in-process
     ```
   - Run with: No daemon

2. **Debug the configuration**:
   - Set breakpoints in ksp-processor code
   - Debug → "Debug KSP Sample"
   - IntelliJ will automatically attach to the debug port

### Quick Iteration Workflow

For rapid development iterations:

1. Make changes to ksp-processor source code
2. Run Maven compile:
   ```bash
   ./mvnw compile -pl ksp-processor
   ```
3. Run Gradle with remote debug (Gradle will wait for debugger)
4. Attach debugger from IntelliJ
5. Step through your changes

**No need to run `mvn install` between iterations!**

## Development Tips

### After Changing KSP Processor Code

```bash
# 1. Compile the processor
./mvnw compile -pl ksp-processor

# 2. Run sample (optional --rerun forces reprocessing)
cd ksp-processor/sample-project
./gradlew :kspKotlin --rerun
```

### View Generated Code

Generated mapper implementations are in:
```
ksp-processor/sample-project/build/generated/ksp/main/java/
```

### Clean Rebuild

```bash
# Clean everything
./gradlew clean
./mvnw clean -pl ksp-processor

# Rebuild
./mvnw compile -pl ksp-processor
./gradlew build
```

## Troubleshooting

### "Could not find org.mapstruct:mapstruct:1.7.0-SNAPSHOT"

Run:
```bash
./mvnw install -DskipTests -pl core
```

### "NoClassDefFoundError: org/mapstruct/ap/internal/..."

Run:
```bash
./mvnw install -DskipTests -pl processor
```

### KSP Processor Not Picking Up Changes

1. Ensure Maven compile succeeded:
   ```bash
   ./mvnw compile -pl ksp-processor
   ```

2. Use `--rerun` flag to force reprocessing:
   ```bash
   ./gradlew :kspKotlin --rerun
   ```

3. Check that `ksp-processor/target/classes` contains your latest changes

## Integration Testing

The sample is automatically built as part of the ksp-processor integration tests:

```bash
./mvnw integration-test -pl ksp-processor
```

This ensures the sample stays in sync with processor changes.
