/**
 *  Copyright 2012-2013 Gunnar Morling (http://www.gunnarmorling.de/)
 *  and/or other contributors as indicated by the @authors tag. See the
 *  copyright.txt file in the distribution for a full listing of all
 *  contributors.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.mapstruct.ap.testutil;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.JavaCompiler.CompilationTask;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.StandardLocation;
import javax.tools.ToolProvider;

import org.mapstruct.ap.MappingProcessor;
import org.mapstruct.ap.testutil.compilation.annotation.CompilationResult;
import org.mapstruct.ap.testutil.compilation.annotation.ExpectedCompilationOutcome;
import org.mapstruct.ap.testutil.compilation.model.CompilationOutcomeDescriptor;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

import static org.fest.assertions.Assertions.assertThat;

/**
 * Base class for all mapper tests. The classes to be compiled for a given test
 * method must be specified via {@link WithClasses}.
 *
 * @author Gunnar Morling
 */
public abstract class MapperTestBase {

    private JavaCompiler compiler;
    private String sourceDir;
    private String classOutputDir;
    private String sourceOutputDir;
    private List<File> classPath;
    private List<String> libraries;
    private DiagnosticCollector<JavaFileObject> diagnostics;

    public MapperTestBase() {
        this.libraries = Arrays.asList( "mapstruct.jar", "guava.jar" );
    }

    @BeforeClass
    public void setup() throws Exception {
        compiler = ToolProvider.getSystemJavaCompiler();

        String basePath = getBasePath();

        sourceDir = basePath + "/src/test/java";
        classOutputDir = basePath + "/target/compilation-tests/classes";
        sourceOutputDir = basePath + "/target/compilation-tests/generated-sources/mapping";

        String testDependenciesDir = basePath + "/target/test-dependencies/";

        classPath = new ArrayList<File>();
        for ( String library : libraries ) {
            classPath.add( new File( testDependenciesDir, library ) );
        }

        createOutputDirs();

        Thread.currentThread().setContextClassLoader(
            new URLClassLoader(
                new URL[] { new File( classOutputDir ).toURI().toURL() },
                Thread.currentThread().getContextClassLoader()
            )
        );
    }

    @BeforeMethod
    public void generateMapperImplementation(Method testMethod) {
        diagnostics = new DiagnosticCollector<JavaFileObject>();
        List<File> sourceFiles = getSourceFiles( getTestClasses( testMethod ) );

        boolean compilationSuccessful = compile( diagnostics, sourceFiles );

        CompilationOutcomeDescriptor actualResult = CompilationOutcomeDescriptor.forResult(
            sourceDir,
            compilationSuccessful,
            diagnostics.getDiagnostics()
        );
        CompilationOutcomeDescriptor expectedResult = CompilationOutcomeDescriptor.forExpectedCompilationResult(
            testMethod.getAnnotation( ExpectedCompilationOutcome.class )
        );

        if ( expectedResult.getCompilationResult() == CompilationResult.SUCCEEDED ) {
            assertThat( actualResult.getCompilationResult() )
                .describedAs( "Compilation failed. Diagnostics: " + diagnostics.getDiagnostics() )
                .isEqualTo( CompilationResult.SUCCEEDED );
        }
        else {
            assertThat( actualResult.getCompilationResult() )
                .describedAs( "Compilation succeeded but should have failed." )
                .isEqualTo( CompilationResult.FAILED );
        }

        assertThat( actualResult.getDiagnostics() ).isEqualTo( expectedResult.getDiagnostics() );
    }

    /**
     * Returns the classes to be compiled for this test.
     *
     * @param testMethod The test method of interest
     *
     * @return A list containing the classes to be compiled for this test
     */
    private List<Class<?>> getTestClasses(Method testMethod) {
        WithClasses withClasses = testMethod.getAnnotation( WithClasses.class );

        if ( withClasses == null ) {
            withClasses = this.getClass().getAnnotation( WithClasses.class );
        }

        if ( withClasses == null || withClasses.value().length == 0 ) {
            throw new IllegalStateException(
                "The classes to be compiled during the test must be specified via @WithClasses."
            );
        }

        return Arrays.asList( withClasses.value() );
    }

    private List<File> getSourceFiles(List<Class<?>> classes) {
        List<File> sourceFiles = new ArrayList<File>( classes.size() );

        for ( Class<?> clazz : classes ) {
            sourceFiles.add(
                new File(
                    sourceDir +
                        File.separator +
                        clazz.getName().replace( ".", File.separator ) +
                        ".java"
                )
            );
        }

        return sourceFiles;
    }

    private boolean compile(DiagnosticCollector<JavaFileObject> diagnostics, Iterable<File> sourceFiles) {
        StandardJavaFileManager fileManager = compiler.getStandardFileManager( null, null, null );

        Iterable<? extends JavaFileObject> compilationUnits = fileManager.getJavaFileObjectsFromFiles( sourceFiles );

        try {
            fileManager.setLocation( StandardLocation.CLASS_PATH, classPath );
            fileManager.setLocation( StandardLocation.CLASS_OUTPUT, Arrays.asList( new File( classOutputDir ) ) );
            fileManager.setLocation( StandardLocation.SOURCE_OUTPUT, Arrays.asList( new File( sourceOutputDir ) ) );
        }
        catch ( IOException e ) {
            throw new RuntimeException( e );
        }

        CompilationTask task = compiler.getTask(
            null,
            fileManager,
            diagnostics,
            Collections.<String>emptyList(),
            null,
            compilationUnits
        );
        task.setProcessors( Arrays.asList( new MappingProcessor() ) );

        return task.call();
    }

    private String getBasePath() {
        try {
            return new File( "." ).getCanonicalPath();
        }
        catch ( IOException e ) {
            throw new RuntimeException( e );
        }
    }

    private void createOutputDirs() {
        File directory = new File( classOutputDir );
        deleteDirectory( directory );
        directory.mkdirs();

        directory = new File( sourceOutputDir );
        deleteDirectory( directory );
        directory.mkdirs();
    }

    private void deleteDirectory(File path) {
        if ( path.exists() ) {
            File[] files = path.listFiles();
            for ( int i = 0; i < files.length; i++ ) {
                if ( files[i].isDirectory() ) {
                    deleteDirectory( files[i] );
                }
                else {
                    files[i].delete();
                }
            }
        }
        path.delete();
    }
}
