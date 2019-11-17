package org.mapstruct.itest.tests;

import static org.gradle.testkit.runner.TaskOutcome.SUCCESS;
import static org.gradle.testkit.runner.TaskOutcome.UP_TO_DATE;
import static org.hamcrest.core.StringContains.containsString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

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
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * <p>This is supposed to be run from the mapstruct root project folder. 
 * Otherwise, use <code>-Dmapstruct_root=path_to_project</code>.
 */
@RunWith( Parameterized.class )
public class GradleIncrementalCompilationTest {
    private static Path ROOT_PATH;
    private static String PROJECT_DIR = "integrationtest/src/test/resources/gradleIncrementalCompilationTest";
    private static String COMPILE_TASK_NAME = "compileJava";

    @Rule
    public final TemporaryFolder testBuildDir = new TemporaryFolder();
    @Rule
    public final TemporaryFolder testProjectDir = new TemporaryFolder();

    private String gradleVersion;
    private GradleRunner runner;
    private File sourceDirectory;
    private List<String> compileArgs; // Gradle compile task arguments

    public GradleIncrementalCompilationTest(String gradleVersion) {
        this.gradleVersion = gradleVersion;
    }

    @Parameters( name = "Gradle {0}" )
    public static List<String> gradleVersions() {
        return Arrays.asList( "5.0", "6.0" );
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

    private List<String> buildCompileArgs() {
        // Make Gradle use the temporary build folder by overriding the buildDir property
        String buildDirPropertyArg = "-PbuildDir=" + testBuildDir.getRoot().getAbsolutePath();

        // Inject the path to the folder containing the mapstruct-processor JAR
        String jarDirectoryArg = "-PmapstructRootPath=" + ROOT_PATH.toString();
        return Arrays.asList( COMPILE_TASK_NAME, buildDirPropertyArg, jarDirectoryArg );
    }

    @BeforeClass
    public static void setupClass() throws Exception {
        ROOT_PATH = Paths.get( System.getProperty( "mapstruct_root", "." ) ).toAbsolutePath();
    }

    @Before
    public void setup() throws IOException {
        // Copy test project files to the temp dir
        Path gradleProjectPath = ROOT_PATH.resolve( PROJECT_DIR );
        FileUtils.copyDirectory( gradleProjectPath.toFile(), testProjectDir.getRoot() );
        compileArgs = buildCompileArgs();
        sourceDirectory = new File( testProjectDir.getRoot(), "src/main/java" );
        runner = GradleRunner.create().withGradleVersion( gradleVersion ).withProjectDir( testProjectDir.getRoot() );
    }

    @Test
    public void testBuildSucceeds() throws IOException {
        // Make sure the test build setup actually compiles
        BuildResult buildResult = getRunner().build();
        assertCompileOutcome( buildResult, SUCCESS );
    }

    @Test
    public void testUpToDate() throws IOException {
        getRunner().build();
        BuildResult secondBuildResult = getRunner().build();
        assertCompileOutcome( secondBuildResult, UP_TO_DATE );
    }

    @Test
    public void testChangeConstant() throws IOException {
        getRunner().build();
        // Change return value in class Target
        File targetFile = new File( sourceDirectory, "org/mapstruct/itest/gradle/model/Target.java" );
        replaceInFile( targetFile, "original", "changed" );
        BuildResult secondBuildResult = getRunner( "--info" ).build();

        // 3 classes should be recompiled: Target -> TestMapper -> TestMapperImpl
        assertRecompiled( secondBuildResult, 3 );
    }

    @Test
    public void testChangeTargetField() throws IOException {
        getRunner().build();
        // Change target field in mapper interface
        File mapperFile = new File( sourceDirectory, "org/mapstruct/itest/gradle/lib/TestMapper.java" );
        replaceInFile( mapperFile, "field", "otherField" );
        BuildResult secondBuildResult = getRunner( "--info" ).build();

        // 2 classes should be recompiled: TestMapper -> TestMapperImpl
        assertRecompiled( secondBuildResult, 2 );
    }

    @Test
    public void testChangeUnrelatedFile() throws IOException {
        getRunner().build();
        File unrelatedFile = new File( sourceDirectory, "org/mapstruct/itest/gradle/lib/UnrelatedComponent.java" );
        replaceInFile( unrelatedFile, "true", "false" );
        BuildResult secondBuildResult = getRunner( "--info" ).build();

        // Only the UnrelatedComponent class should be recompiled
        assertRecompiled( secondBuildResult, 1 );
    }
}
