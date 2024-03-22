/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.mapstruct.ap

import androidx.room.compiler.processing.javac.JavacBasicAnnotationProcessor
import javax.lang.model.SourceVersion

/**
 * Annotation processor option to tell Gradle that Room is an isolating annotation processor.
 */
private const val ISOLATING_ANNOTATION_PROCESSORS_INDICATOR =
    "org.gradle.annotation.processing.isolating"

/**
 * The annotation processor for Room.
 */
class MapstructJavacProcessor : JavacBasicAnnotationProcessor(
//    configureEnv = { options ->
//        DatabaseProcessingStep.getEnvConfig(options)
//    }
) {

    /** Helper variable to avoid reporting the warning twice. */
    private var jdkVersionHasBugReported = false

    override fun processingSteps() = listOf(
        MappingProcessor()
    )

    override fun getSupportedOptions(): MutableSet<String> {
//        val supportedOptions = Context.ARG_OPTIONS.toMutableSet()
//        if (Context.BooleanProcessorOptions.INCREMENTAL.getValue(xProcessingEnv)) {
//            if (methodParametersVisibleInClassFiles()) {
//                // Room can be incremental
//                supportedOptions.add(ISOLATING_ANNOTATION_PROCESSORS_INDICATOR)
//            } else {
//                if (!jdkVersionHasBugReported) {
//                    Context(xProcessingEnv).logger.w(
//                        Warning.JDK_VERSION_HAS_BUG, ProcessorErrors.JDK_VERSION_HAS_BUG
//                    )
//                    jdkVersionHasBugReported = true
//                }
//            }
//        }

//        return supportedOptions
        return mutableSetOf()
    }

    /**
     * Returns `true` if the method parameters in class files can be accessed by Room.
     *
     * Context: Room requires access to the real parameter names of constructors (see
     * PojoProcessor.getParamNames). Room uses the ExecutableElement.getParemters() API on the
     * constructor element to do this.
     *
     * When Room is not yet incremental, the above API is working as expected. However, if we make
     * Room incremental, during an incremental compile Gradle may want to pass class files instead
     * source files to annotation processors (to avoid recompiling the source files that haven't
     * changed). Due to JDK bug https://bugs.openjdk.java.net/browse/JDK-8007720, the class files
     * may lose the real parameter names of constructors, which would break Room.
     *
     * The above JDK bug was fixed in JDK 11. The fix was also cherry-picked back into the
     * embedded JDK that was shipped with Android Studio 3.5+.
     *
     * Therefore, for Room to be incremental, we need to check whether the JDK being used has the
     * fix: Either it is JDK 11+ or it is an embedded JDK that has the cherry-picked fix (version
     * 1.8.0_202-release-1483-b39-5509098 or higher).
     */
//    private fun methodParametersVisibleInClassFiles(): Boolean {
//        val currentJavaVersion = SimpleJavaVersion.getCurrentVersion() ?: return false
//
//        if (currentJavaVersion >= SimpleJavaVersion.VERSION_11_0_0) {
//            return true
//        }
//
//        val isEmbeddedJdk =
//            System.getProperty("java.vendor")?.contains("JetBrains", ignoreCase = true)
//                ?: false
//        // We are interested in 3 ranges of Android Studio (AS) versions:
//        //    1. AS 3.5.0-alpha09 and lower use JDK 1.8.0_152 or lower.
//        //    2. AS 3.5.0-alpha10 up to 3.5.0-beta01 use JDK 1.8.0_202-release-1483-b39-5396753.
//        //    3. AS 3.5.0-beta02 and higher use JDK 1.8.0_202-release-1483-b39-5509098 or higher,
//        //       which have the cherry-picked JDK fix.
//        // Therefore, if the JDK version is 1.8.0_202, we need to filter out those in range #2.
//        return if (isEmbeddedJdk && (currentJavaVersion > SimpleJavaVersion.VERSION_1_8_0_202)) {
//            true
//        } else if (isEmbeddedJdk && (currentJavaVersion == SimpleJavaVersion.VERSION_1_8_0_202)) {
//            System.getProperty("java.runtime.version")
//                ?.let { it != "1.8.0_202-release-1483-b39-5396753" }
//                ?: false
//        } else {
//            false
//        }
//    }

    override fun getSupportedSourceVersion(): SourceVersion {
        return SourceVersion.latest()
    }

//    override fun postRound(env: XProcessingEnv, round: XRoundEnv) {
//        if (round.isProcessingOver) {
//            DatabaseVerifier.cleanup()
//        }
//    }
}
