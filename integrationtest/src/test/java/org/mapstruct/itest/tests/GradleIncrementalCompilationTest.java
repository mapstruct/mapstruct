package org.mapstruct.itest.tests;

import static org.gradle.testkit.runner.TaskOutcome.SUCCESS;
import static org.gradle.testkit.runner.TaskOutcome.UP_TO_DATE;
import static org.hamcrest.core.StringContains.containsString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.gradle.testkit.runner.BuildResult;
import org.gradle.testkit.runner.GradleRunner;
import org.gradle.testkit.runner.TaskOutcome;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

public class GradleIncrementalCompilationTest {
    private static String PROJECT_DIR = "src/test/resources/gradleIncrementalCompilationTest";
    private static String COMPILE_TASK_NAME = "compileJava";
    private static String GRADLE_DISTRIBUTION_VERSION = "5.0";

    @Rule
    public final TemporaryFolder testBuildDir = new TemporaryFolder();
    @Rule
    public final TemporaryFolder testProjectDir = new TemporaryFolder();
    private GradleRunner runner;
    // The source file to change
    private File targetFile; 
    // Gradle compile task arguments
    private List<String> compileArgs; 

    @Before
    public void setup() throws IOException {
        // Copy test project files to the temp dir
        FileUtils.copyDirectory( new File( PROJECT_DIR ), testProjectDir.getRoot() );
        buildCompileArgs();
        File sourceDirectory = new File( testProjectDir.getRoot(), "src/main/java" );
        targetFile = new File( sourceDirectory, "org/mapstruct/itest/gradle/model/Target.java" );
        runner = GradleRunner
                             .create()
                             .withGradleVersion( GRADLE_DISTRIBUTION_VERSION )
                             .withProjectDir( testProjectDir.getRoot() );
    }

    private void buildCompileArgs() {
        // Make Gradle use the temporary build folder by overriding the buildDir property
        String buildDirPropertyArg = "-PbuildDir=" + testBuildDir.getRoot().getAbsolutePath();
        File rootDirectory = new File("../");
        
        // Inject the path to the folder containing the mapstruct-processor JAR
        String jarDirectoryArg = "-PmapstructRootPath=" + rootDirectory.getAbsolutePath();
        compileArgs = Arrays.asList( COMPILE_TASK_NAME, buildDirPropertyArg, jarDirectoryArg);
    }

    private void replaceInFile(File file, CharSequence target, CharSequence replacement) throws IOException {
        String content = FileUtils.readFileToString( file, Charset.defaultCharset() );
        FileUtils.writeStringToFile( file, content.replace( target, replacement ), Charset.defaultCharset() );
    }

    private GradleRunner getRunner(String... additionalArguments) {
        List<String> fullArguments = new ArrayList<String>( compileArgs );
        fullArguments.addAll( Arrays.asList( additionalArguments ) );
        return runner.withArguments( fullArguments );
    }

    private void assertCompileOutcome(BuildResult result, TaskOutcome outcome) {
        assertEquals( outcome, result.task( ":" + COMPILE_TASK_NAME ).getOutcome() );
    }

    private void assertRecompiled(BuildResult result, int recompiledCount) {
        assertCompileOutcome( result, recompiledCount > 0 ? SUCCESS : UP_TO_DATE );
        assertThat(
            result.getOutput(),
            containsString( String.format( "Incremental compilation of %d classes completed", recompiledCount ) ) );
    }

    @Test
    public void testUpToDate() throws IOException {
        BuildResult result = getRunner().build();
        System.out.println( result.getOutput() );
        assertCompileOutcome( result, SUCCESS );

        BuildResult secondBuildResult = getRunner().build();
        assertCompileOutcome( secondBuildResult, UP_TO_DATE );
    }

    @Test
    public void testChangedFile() throws IOException {
        BuildResult result = getRunner( "--info" ).build();
        System.out.println( result.getOutput() );
        assertCompileOutcome( result, SUCCESS );

        System.out.println( "### SECOND BUILD FOLLOWS\n" );

        // Change return value in class Target
        replaceInFile( targetFile, "original", "change" );

        BuildResult secondBuildResult = getRunner( "--info" ).build();
        System.out.println( secondBuildResult.getOutput() );

        // 3 classes should be recompiled: Target -> TestMapper -> TestMapperImpl
        assertRecompiled( secondBuildResult, 3 );
    }
}
