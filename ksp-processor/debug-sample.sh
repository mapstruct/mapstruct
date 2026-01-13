#!/bin/bash
#
# Copyright MapStruct Authors.
#
# Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
#

# ABOUTME: Debug script for KSP processor that compiles Maven module and runs sample with remote debugging enabled
# ABOUTME: Simplifies the debugging workflow to a single command execution

set -e  # Exit on error

echo "🔨 Step 1/2: Compiling ksp-processor..."
cd "$(dirname "$0")/.."
./mvnw compile -pl ksp-processor

echo ""
echo "🐛 Step 2/2: Running sample-project with remote debugging..."
echo "⏳ Gradle will wait on port 5005 for debugger to attach"
echo "👉 In IntelliJ: Run → Attach to Process → Select the Gradle/Kotlin process"
echo ""

cd ksp-processor/sample-project
./gradlew clean kspKotlin --rerun \
  -Dorg.gradle.debug=true \
  --no-daemon \
  -Pkotlin.compiler.execution.strategy=in-process

echo ""
echo "✅ Debug session completed successfully!"
