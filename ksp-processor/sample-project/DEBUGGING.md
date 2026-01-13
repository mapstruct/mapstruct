# Quick Debugging Guide

## TL;DR - Single-Click Debugging

### Option 1: IntelliJ Run Configuration (Easiest)

1. **Set breakpoints** in ksp-processor source code
2. **Run → "Debug KSP Sample"** (run configuration included in project)
3. **Wait** for "Listening for transport dt_socket at address: 5005"
4. **Run → Attach to Process** → Select the Gradle/Kotlin process
5. Debug! 🐛

### Option 2: Command Line

```bash
# From project root
./ksp-processor/debug-sample.sh

# Then: Run → Attach to Process → Select Gradle process
```

### Option 3: Manual Steps (for understanding)

```bash
# 1. Compile ksp-processor
cd /Users/leonid/work/jb/mapstruct
./mvnw compile -pl ksp-processor

# 2. Run with remote debugger
cd ksp-processor/sample-project
./gradlew :kspKotlin --rerun \
  -Dorg.gradle.debug=true \
  --no-daemon \
  -Pkotlin.compiler.execution.strategy=in-process

# 3. In IntelliJ: Run → Attach to Process → Select Gradle process
# Set breakpoints in ksp-processor source and debug!
```

## What Changed?

### Before (Old Workflow)
```bash
# Make changes to ksp-processor
vim src/main/kotlin/...

# SLOW: Publish to mavenLocal
./mvnw clean install -pl ksp-processor

# Run sample from external directory
cd /Users/leonid/work/jb/samples/mapstruct-ksp-sample
./gradlew kspKotlin --rerun -Dorg.gradle.debug=true
```

**Problems:**
- ❌ Had to run `mvn install` after every change
- ❌ Sample project was in separate directory
- ❌ Easy to forget to publish before testing
- ❌ Slower iteration cycle

### After (New Workflow)
```bash
# Make changes to ksp-processor
vim src/main/kotlin/...

# FAST: Just compile
./mvnw compile -pl ksp-processor

# Run sample from subproject
cd ksp-processor/sample-project
./gradlew kspKotlin --rerun -Dorg.gradle.debug=true
```

**Benefits:**
- ✅ No `mvn install` needed
- ✅ Sample integrated as subproject
- ✅ Direct dependency on compiled classes
- ✅ Faster iteration cycle
- ✅ Easier to keep in sync

## How It Works

The sample's `build.gradle.kts` now references the compiled processor directly:

```kotlin
dependencies {
    ksp(files("../target/classes"))  // Direct reference to Maven build
    ksp("org.mapstruct:mapstruct-processor:1.7.0-SNAPSHOT")
}
```

This means Gradle uses the classes from `ksp-processor/target/classes` directly, which are updated by `mvn compile`.

## Remote Debugging Command Breakdown

```bash
./gradlew :kspKotlin --rerun \
  -Dorg.gradle.debug=true \              # Enable debug mode
  --no-daemon \                           # Don't use Gradle daemon
  -Pkotlin.compiler.execution.strategy=in-process  # Run in same process
```

- **`--rerun`**: Forces KSP to reprocess even if nothing changed
- **`-Dorg.gradle.debug=true`**: Gradle waits on port 5005 for debugger
- **`--no-daemon`**: Ensures clean debug session
- **`in-process`**: Kotlin compiler runs in same process (easier debugging)

## IntelliJ IDEA Setup

### Quick Method: Attach to Process

1. Run the Gradle command above (it will wait)
2. **Run → Attach to Process...**
3. Find process with "GradleDaemon" or "kotlin"
4. Select and attach
5. Set breakpoints and debug!

### Alternative: Remote JVM Debug Configuration

1. **Run → Edit Configurations → + → Remote JVM Debug**
2. **Name**: "Debug KSP Sample"
3. **Port**: 5005 (default)
4. **Host**: localhost
5. Save and use Debug button after running Gradle command

## Common Scenarios

### I changed the processor code

```bash
./mvnw compile -pl ksp-processor
cd ksp-processor/sample-project && ./gradlew kspKotlin --rerun
```

### I want to debug a specific issue

```bash
# 1. Set breakpoints in IntelliJ
# 2. Run with debug
./mvnw compile -pl ksp-processor
cd ksp-processor/sample-project
./gradlew kspKotlin --rerun -Dorg.gradle.debug=true --no-daemon -Pkotlin.compiler.execution.strategy=in-process
# 3. Attach debugger
```

### I want to test on different sample code

Edit `ksp-processor/sample-project/src/main/kotlin/Mapper.kt` and re-run Gradle.

## Troubleshooting

### Gradle finishes immediately without waiting

You might be using the Gradle daemon. Add `--no-daemon`:
```bash
./gradlew kspKotlin --rerun -Dorg.gradle.debug=true --no-daemon
```

### Breakpoints not hitting

1. Ensure `mvn compile` completed successfully
2. Check that `ksp-processor/target/classes` has your latest changes
3. Verify debugger is attached to correct process
4. Try adding `-Pkotlin.compiler.execution.strategy=in-process`

### "NoClassDefFoundError" when running sample

The base processor might not be published. Run once:
```bash
./mvnw install -DskipTests -pl processor
```

## Pro Tips

1. **Single-click debugging:**
   - Use the "Debug KSP Sample" run configuration
   - No need to manage multiple terminals
   - Script handles compilation + debug setup automatically

2. **Use IntelliJ's Rerun:**
   After attaching debugger once, you can click "Rerun" on the shell script configuration to quickly restart the debug session.

3. **Quick recompile-only:**
   ```bash
   ./mvnw compile -pl ksp-processor
   ```
   Then rerun the Gradle command from the script output.

4. **View generated code:**
   ```bash
   cat ksp-processor/sample-project/build/generated/ksp/main/java/org/example/CarMapperImpl.java
   ```

5. **Clean state:**
   ```bash
   ./gradlew clean && ./mvnw clean -pl ksp-processor && ./mvnw compile -pl ksp-processor && ./gradlew build
   ```
