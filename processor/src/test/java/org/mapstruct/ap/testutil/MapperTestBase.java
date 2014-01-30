/**
 *  Copyright 2012-2014 Gunnar Morling (http://www.gunnarmorling.de/)
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

import static org.fest.assertions.Assertions.assertThat;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

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
import org.mapstruct.ap.testutil.compilation.annotation.ProcessorOption;
import org.mapstruct.ap.testutil.compilation.model.CompilationOutcomeDescriptor;
import org.mapstruct.ap.testutil.compilation.model.DiagnosticDescriptor;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

/**
 * Base class for all mapper tests.
 * </p>
 * The classes to be compiled for a given test method must be specified via
 * {@link WithClasses}. In addition the following things can be configured
 * optionally :
 * <ul>
 * <li>Processor options to be considered during compilation via
 * {@link ProcessorOption}.</li>
 * <li>The expected compilation outcome and expected diagnostics can be
 * specified via {@link ExpectedCompilationOutcome}. If no outcome is specified,
 * a successful compilation is assumed.</li>
 * </ul>
 *
 * @author Gunnar Morling
 */
public abstract class MapperTestBase {

    private static final String LINE_SEPARATOR = System.getProperty( "line.separator" );
    private static final DiagnosticDescriptorComparator COMPARATOR = new DiagnosticDescriptorComparator();

    private JavaCompiler compiler;
    private String sourceDir;
    private String classOutputDir;
    private String sourceOutputDir;
    private List<File> classPath;
    private final List<String> libraries;
    private DiagnosticCollector<JavaFileObject> diagnostics;

    public MapperTestBase() {
        this.libraries = Arrays.asList( "mapstruct.jar", "guava.jar", "javax.inject.jar" );
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
        Set<File> sourceFiles = getSourceFiles( getTestClasses( testMethod ) );
        List<String> processorOptions = getProcessorOptions( testMethod );

        boolean compilationSuccessful = compile( diagnostics, sourceFiles, processorOptions );

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

        assertDiagnostics( actualResult.getDiagnostics(), expectedResult.getDiagnostics() );
    }

    private void assertDiagnostics(List<DiagnosticDescriptor> actualDiagnostics,
                                   List<DiagnosticDescriptor> expectedDiagnostics) {

        Collections.sort( actualDiagnostics, COMPARATOR );
        Collections.sort( expectedDiagnostics, COMPARATOR );

        Iterator<DiagnosticDescriptor> actualIterator = actualDiagnostics.iterator();
        Iterator<DiagnosticDescriptor> expectedIterator = expectedDiagnostics.iterator();

        assertThat( actualDiagnostics ).describedAs(
            String.format(
                "Numbers of expected and actual diagnostics are diffent. Actual:%s%s%sExpected:%s%s.",
                LINE_SEPARATOR,
                actualDiagnostics.toString().replace( ", ", LINE_SEPARATOR ),
                LINE_SEPARATOR,
                LINE_SEPARATOR,
                expectedDiagnostics.toString().replace( ", ", LINE_SEPARATOR )
            )
        ).hasSize( expectedDiagnostics.size() );

        while ( actualIterator.hasNext() ) {

            DiagnosticDescriptor actual = actualIterator.next();
            DiagnosticDescriptor expected = expectedIterator.next();

            assertThat( actual.getSourceFileName() ).isEqualTo( expected.getSourceFileName() );
            assertThat( actual.getLine() ).isEqualTo( expected.getLine() );
            assertThat( actual.getKind() ).isEqualTo( expected.getKind() );
            assertThat( actual.getMessage() ).describedAs(
                String.format(
                    "Unexpected message for diagnostic %s:%s %s",
                    actual.getSourceFileName(),
                    actual.getLine(),
                    actual.getKind()
                )
            ).matches( ".*" + expected.getMessage() + ".*" );
        }
    }

    /**
     * Returns the classes to be compiled for this test.
     *
     * @param testMethod The test method of interest
     *
     * @return A set containing the classes to be compiled for this test
     */
    private Set<Class<?>> getTestClasses(Method testMethod) {
        Set<Class<?>> testClasses = new HashSet<Class<?>>();

        WithClasses withClasses = testMethod.getAnnotation( WithClasses.class );
        if ( withClasses != null ) {
            testClasses.addAll( Arrays.asList( withClasses.value() ) );
        }

        withClasses = this.getClass().getAnnotation( WithClasses.class );
        if ( withClasses != null ) {
            testClasses.addAll( Arrays.asList( withClasses.value() ) );
        }

        if ( testClasses.isEmpty() ) {
            throw new IllegalStateException(
                "The classes to be compiled during the test must be specified via @WithClasses."
            );
        }

        return testClasses;
    }

    /**
     * Returns the processor options to be used this test.
     *
     * @param testMethod The test method of interest
     *
     * @return A list containing the processor options to be used for this test
     */
    private List<String> getProcessorOptions(Method testMethod) {
        ProcessorOption processorOption = testMethod.getAnnotation( ProcessorOption.class );

        if ( processorOption == null ) {
            processorOption = this.getClass().getAnnotation( ProcessorOption.class );
        }

        return processorOption != null ? Arrays.asList( asOptionString( processorOption ) ) :
            Collections.<String>emptyList();
    }

    private String asOptionString(ProcessorOption processorOption) {
        return String.format( "-A%s=%s", processorOption.name(), processorOption.value() );
    }

    private Set<File> getSourceFiles(Collection<Class<?>> classes) {
        Set<File> sourceFiles = new HashSet<File>( classes.size() );

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

    private boolean compile(DiagnosticCollector<JavaFileObject> diagnostics, Iterable<File> sourceFiles,
                            List<String> processorOptions) {
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
            processorOptions,
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

    private static class DiagnosticDescriptorComparator implements Comparator<DiagnosticDescriptor> {

        @Override
        public int compare(DiagnosticDescriptor o1, DiagnosticDescriptor o2) {
            String sourceFileName1 = o1.getSourceFileName() != null ? o1.getSourceFileName() : "";
            String sourceFileName2 = o2.getSourceFileName() != null ? o2.getSourceFileName() : "";

            int result = sourceFileName1.compareTo( sourceFileName2 );

            if ( result != 0 ) {
                return result;
            }
            result = Long.valueOf( o1.getLine() ).compareTo( o2.getLine() );
            if ( result != 0 ) {
                return result;
            }

            // Using the message is not perfect when using regular expressions,
            // but it's better than nothing
            return o1.getMessage().compareTo( o2.getMessage() );
        }
    }
}
