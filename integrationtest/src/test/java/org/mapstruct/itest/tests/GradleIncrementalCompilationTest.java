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

import org.apache.commons.io.FileUtils;
import org.gradle.testkit.runner.BuildResult;
import org.gradle.testkit.runner.GradleRunner;
import org.gradle.testkit.runner.TaskOutcome;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.condition.DisabledForJreRange;
import org.junit.jupiter.api.condition.JRE;
import org.junit.jupiter.api.io.TempDir;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.runners.Parameterized.Parameters;

import static org.gradle.testkit.runner.TaskOutcome.SUCCESS;
import static org.gradle.testkit.runner.TaskOutcome.UP_TO_DATE;
import static org.hamcrest.core.StringContains.containsString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

/**
 * <p>This is supposed to be run from the mapstruct root project folder.
 * Otherwise, use <code>-Dmapstruct_root=path_to_project</code>.
 */
@DisabledForJreRange(min = JRE.JAVA_11)
public class GradleIncrementalCompilationTest {
    private static Path rootPath;
    private static String projectDir = "integrationtest/src/test/resources/gradleIncrementalCompilationTest";
    private static String compileTaskName = "compileJava";

    @TempDir
    File testBuildDir;
    @TempDir
    File testProjectDir;

    private GradleRunner runner;
    private File sourceDirectory;
    private List<String> compileArgs; // Gradle compile task arguments

    @Parameters(name = "Gradle {0}")
    public static List<String> gradleVersions() {
        return Arrays.asList( "5.0", "6.0" );
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
        assertEquals( outcome, result.task( ":" + compileTaskName ).getOutcome() );
    }

    private void assertRecompiled(BuildResult result, int recompiledCount) {
        assertCompileOutcome( result, recompiledCount > 0 ? SUCCESS : UP_TO_DATE );
        assertThat(
            result.getOutput(),
            containsString( String.format( "Incremental compilation of %d classes completed", recompiledCount ) )
        );
    }

    private List<String> buildCompileArgs() {
        // Make Gradle use the temporary build folder by overriding the buildDir property
        String buildDirPropertyArg = "-PbuildDir=" + testBuildDir.getAbsolutePath();

        // Inject the path to the folder containing the mapstruct-processor JAR
        String jarDirectoryArg = "-PmapstructRootPath=" + rootPath.toString();
        return Arrays.asList( compileTaskName, buildDirPropertyArg, jarDirectoryArg );
    }

    @BeforeAll
    public static void setupClass() throws Exception {
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
        Path gradleProjectPath = rootPath.resolve( projectDir );
        FileUtils.copyDirectory( gradleProjectPath.toFile(), testProjectDir );
        compileArgs = buildCompileArgs();
        sourceDirectory = new File( testProjectDir, "src/main/java" );
        runner = GradleRunner.create().withGradleVersion( gradleVersion ).withProjectDir( testProjectDir );
    }

    @ParameterizedTest
    @MethodSource("gradleVersions")
    public void testBuildSucceeds(String gradleVersion) throws IOException {
        setup( gradleVersion );
        // Make sure the test build setup actually compiles
        BuildResult buildResult = getRunner().build();
        assertCompileOutcome( buildResult, SUCCESS );
    }

    @ParameterizedTest
    @MethodSource("gradleVersions")
    public void testUpToDate(String gradleVersion) throws IOException {
        setup( gradleVersion );
        getRunner().build();
        BuildResult secondBuildResult = getRunner().build();
        assertCompileOutcome( secondBuildResult, UP_TO_DATE );
    }

    @ParameterizedTest
    @MethodSource("gradleVersions")
    public void testChangeConstant(String gradleVersion) throws IOException {
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
    public void testChangeTargetField(String gradleVersion) throws IOException {
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
    public void testChangeUnrelatedFile(String gradleVersion) throws IOException {
        setup( gradleVersion );
        getRunner().build();
        File unrelatedFile = new File( sourceDirectory, "org/mapstruct/itest/gradle/lib/UnrelatedComponent.java" );
        replaceInFile( unrelatedFile, "true", "false" );
        BuildResult secondBuildResult = getRunner( "--info" ).build();

        // Only the UnrelatedComponent class should be recompiled
        assertRecompiled( secondBuildResult, 1 );
    }
}
