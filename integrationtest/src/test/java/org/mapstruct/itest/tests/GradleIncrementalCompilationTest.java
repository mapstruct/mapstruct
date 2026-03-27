/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.itest.tests;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import org.apache.commons.io.FileUtils;
import org.gradle.testkit.runner.BuildResult;
import org.gradle.testkit.runner.GradleRunner;
import org.gradle.testkit.runner.TaskOutcome;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Named;
import org.junit.jupiter.api.condition.DisabledForJreRange;
import org.junit.jupiter.api.condition.JRE;
import org.junit.jupiter.api.io.TempDir;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.gradle.testkit.runner.TaskOutcome.SUCCESS;
import static org.gradle.testkit.runner.TaskOutcome.UP_TO_DATE;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * <p>This is supposed to be run from the mapstruct root project folder.
 * Otherwise, use <code>-Dmapstruct_root=path_to_project</code>.
 */
@DisabledForJreRange(min = JRE.JAVA_11)
class GradleIncrementalCompilationTest {
    private static Path rootPath;
    private static final String PROJECT_DIR = "integrationtest/src/test/resources/gradleIncrementalCompilationTest";
    private static final String COMPILE_TASK_NAME = "compileJava";

    @TempDir
    private File testBuildDir;
    @TempDir
    private File testProjectDir;

    private GradleRunner runner;
    private File sourceDirectory;
    private List<String> compileArgs; // Gradle compile task arguments

    static Stream<Arguments> gradleVersions() {
        return Stream.of(
                Arguments.of( Named.of( "Gradle 5.0", "5.0" ) ),
                Arguments.of( Named.of( "Gradle 6.0", "6.0" ) ) );
    }

    private void replaceInFile(File file, CharSequence target, CharSequence replacement) throws IOException {
        String content = FileUtils.readFileToString( file, Charset.defaultCharset() );
        FileUtils.writeStringToFile( file, content.replace( target, replacement ), Charset.defaultCharset() );
    }

    private GradleRunner getRunner(String... additionalArguments) {
        List<String> fullArguments = new ArrayList<>(compileArgs);
        fullArguments.addAll( Arrays.asList( additionalArguments ) );
        return runner.withArguments( fullArguments );
    }

    private void assertCompileOutcome(BuildResult result, TaskOutcome outcome) {
        assertEquals( outcome, result.task( ":" + COMPILE_TASK_NAME ).getOutcome() );
    }

    private void assertRecompiled(BuildResult result, int recompiledCount) {
        assertCompileOutcome( result, recompiledCount > 0 ? SUCCESS : UP_TO_DATE );
        assertThat( result.getOutput() )
                .contains( String.format( "Incremental compilation of %d classes completed", recompiledCount ) );
    }

    private List<String> buildCompileArgs() {
        // Make Gradle use the temporary build folder by overriding the buildDir property
        String buildDirPropertyArg = "-PbuildDir=" + testBuildDir.getAbsolutePath();

        // Inject the path to the folder containing the mapstruct-processor JAR
        String jarDirectoryArg = "-PmapstructRootPath=" + rootPath.toString();
        return Arrays.asList( COMPILE_TASK_NAME, buildDirPropertyArg, jarDirectoryArg );
    }

    @BeforeAll
    static void setupClass() {
        rootPath = Paths.get( System.getProperty( "mapstruct_root", "." ) ).toAbsolutePath();
    }

    public void setup(String gradleVersion) throws IOException {
        if ( !testBuildDir.exists() ) {
            testBuildDir.mkdirs();
        }

        if ( !testProjectDir.exists() ) {
            testProjectDir.mkdirs();
        }
        // Copy test project files to the temp dir
        Path gradleProjectPath = rootPath.resolve( PROJECT_DIR );
        FileUtils.copyDirectory( gradleProjectPath.toFile(), testProjectDir );
        compileArgs = buildCompileArgs();
        sourceDirectory = new File( testProjectDir, "src/main/java" );
        runner = GradleRunner.create().withGradleVersion( gradleVersion ).withProjectDir( testProjectDir );
    }

    @ParameterizedTest
    @MethodSource("gradleVersions")
    void testBuildSucceeds(String gradleVersion) throws IOException {
        setup( gradleVersion );
        // Make sure the test build setup actually compiles
        BuildResult buildResult = getRunner().build();
        assertCompileOutcome( buildResult, SUCCESS );
    }

    @ParameterizedTest
    @MethodSource("gradleVersions")
    void testUpToDate(String gradleVersion) throws IOException {
        setup( gradleVersion );
        getRunner().build();
        BuildResult secondBuildResult = getRunner().build();
        assertCompileOutcome( secondBuildResult, UP_TO_DATE );
    }

    @ParameterizedTest
    @MethodSource("gradleVersions")
    void testChangeConstant(String gradleVersion) throws IOException {
        setup( gradleVersion );
        getRunner().build();
        // Change return value in class Target
        File targetFile = new File( sourceDirectory, "org/mapstruct/itest/gradle/model/Target.java" );
        replaceInFile( targetFile, "original", "changed" );
        BuildResult secondBuildResult = getRunner( "--info" ).build();

        // 3 classes should be recompiled: Target -> TestMapper -> TestMapperImpl
        assertRecompiled( secondBuildResult, 3 );
    }

    @ParameterizedTest
    @MethodSource("gradleVersions")
    void testChangeTargetField(String gradleVersion) throws IOException {
        setup( gradleVersion );
        getRunner().build();
        // Change target field in mapper interface
        File mapperFile = new File( sourceDirectory, "org/mapstruct/itest/gradle/lib/TestMapper.java" );
        replaceInFile( mapperFile, "field", "otherField" );
        BuildResult secondBuildResult = getRunner( "--info" ).build();

        // 2 classes should be recompiled: TestMapper -> TestMapperImpl
        assertRecompiled( secondBuildResult, 2 );
    }

    @ParameterizedTest
    @MethodSource("gradleVersions")
    void testChangeUnrelatedFile(String gradleVersion) throws IOException {
        setup( gradleVersion );
        getRunner().build();
        File unrelatedFile = new File( sourceDirectory, "org/mapstruct/itest/gradle/lib/UnrelatedComponent.java" );
        replaceInFile( unrelatedFile, "true", "false" );
        BuildResult secondBuildResult = getRunner( "--info" ).build();

        // Only the UnrelatedComponent class should be recompiled
        assertRecompiled( secondBuildResult, 1 );
    }
}
