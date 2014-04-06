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
package org.mapstruct.ap.testutil.runner;

import static org.fest.assertions.Assertions.assertThat;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.JavaCompiler.CompilationTask;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.StandardLocation;
import javax.tools.ToolProvider;

import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;
import org.mapstruct.ap.MappingProcessor;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.compilation.annotation.CompilationResult;
import org.mapstruct.ap.testutil.compilation.annotation.ExpectedCompilationOutcome;
import org.mapstruct.ap.testutil.compilation.annotation.ProcessorOption;
import org.mapstruct.ap.testutil.compilation.model.CompilationOutcomeDescriptor;
import org.mapstruct.ap.testutil.compilation.model.DiagnosticDescriptor;

/**
 * A JUnit4 statement that performs source generation using the annotation processor and compiles those sources.
 *
 * @author Andreas Gudian
 */
class CompilingStatement extends Statement {

    /**
     * Property to specify the sub-directory below /target/ where the generated files are placed
     */
    public static final String MAPPER_TEST_OUTPUT_DIR_PROPERTY = "mapper.test.output.dir";
    private static final String TARGET_COMPILATION_TESTS = "/target/"
        + System.getProperty( MAPPER_TEST_OUTPUT_DIR_PROPERTY, "compilation-tests" ) + "_";

    private static final String LINE_SEPARATOR = System.getProperty( "line.separator" );
    private static final DiagnosticDescriptorComparator COMPARATOR = new DiagnosticDescriptorComparator();

    private static final ThreadLocal<Integer> THREAD_NUMBER = new ThreadLocal<Integer>() {
        private final AtomicInteger nextThreadId = new AtomicInteger( 0 );

        @Override
        protected Integer initialValue() {
            return nextThreadId.getAndIncrement();
        }
    };

    /**
     * Caches the outcome of given compilations. That way we avoid the repeated compilation of the same source files for
     * several test methods of one test class.
     */
    private static final ThreadLocal<CompilationCache> COMPILATION_CACHE = new ThreadLocal<CompilationCache>() {
        @Override
        protected CompilationCache initialValue() {
            return new CompilationCache();
        }
    };

    private static final List<String> LIBRARIES = Arrays.asList( "mapstruct.jar", "guava.jar", "javax.inject.jar" );

    private final Statement next;
    private final FrameworkMethod method;
    private final ModifiableURLClassLoader classloader;

    private JavaCompiler compiler;
    private String sourceDir;
    private String classOutputDir;
    private String sourceOutputDir;
    private List<File> classPath;

    public CompilingStatement(Statement next, FrameworkMethod method, ModifiableURLClassLoader classloader) {
        this.next = next;
        this.method = method;
        this.classloader = classloader;
    }

    @Override
    public void evaluate() throws Throwable {
        generateMapperImplementation();

        next.evaluate();
    }

    protected void setupCompiler() throws Exception {
        compiler = ToolProvider.getSystemJavaCompiler();

        String basePath = getBasePath();

        Integer i = THREAD_NUMBER.get();

        sourceDir = basePath + "/src/test/java";
        classOutputDir = basePath + TARGET_COMPILATION_TESTS + i + "/classes";
        sourceOutputDir = basePath + TARGET_COMPILATION_TESTS + i + "/generated-sources/mapping";

        String testDependenciesDir = basePath + "/target/test-dependencies/";

        classPath = new ArrayList<File>();
        for ( String library : LIBRARIES ) {
            classPath.add( new File( testDependenciesDir, library ) );
        }

        createOutputDirs();

        classloader.addOutputDir( classOutputDir );
    }

    protected void generateMapperImplementation() throws Exception {
        CompilationResultHolder compilationResult = compile( getTestClasses(), getProcessorOptions() );

        CompilationOutcomeDescriptor actualResult =
            CompilationOutcomeDescriptor.forResult(
                sourceDir,
                compilationResult.compilationSuccessful,
                compilationResult.diagnostics.getDiagnostics()
            );
        CompilationOutcomeDescriptor expectedResult =
            CompilationOutcomeDescriptor.forExpectedCompilationResult(
                method.getAnnotation( ExpectedCompilationOutcome.class )
            );

        if ( expectedResult.getCompilationResult() == CompilationResult.SUCCEEDED ) {
            assertThat( actualResult.getCompilationResult() ).describedAs(
                "Compilation failed. Diagnostics: " + compilationResult.diagnostics.getDiagnostics()
            ).isEqualTo(
                CompilationResult.SUCCEEDED
            );
        }
        else {
            assertThat( actualResult.getCompilationResult() ).describedAs(
                "Compilation succeeded but should have failed."
            ).isEqualTo( CompilationResult.FAILED );
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
        ).hasSize(
            expectedDiagnostics.size()
        );

        while ( actualIterator.hasNext() ) {

            DiagnosticDescriptor actual = actualIterator.next();
            DiagnosticDescriptor expected = expectedIterator.next();

            if ( expected.getSourceFileName() != null ) {
                assertThat( actual.getSourceFileName() ).isEqualTo( expected.getSourceFileName() );
            }
            if ( expected.getLine() != null ) {
                assertThat( actual.getLine() ).isEqualTo( expected.getLine() );
            }
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
    private Set<Class<?>> getTestClasses() {
        Set<Class<?>> testClasses = new HashSet<Class<?>>();

        WithClasses withClasses = method.getAnnotation( WithClasses.class );
        if ( withClasses != null ) {
            testClasses.addAll( Arrays.asList( withClasses.value() ) );
        }

        withClasses = method.getMethod().getDeclaringClass().getAnnotation( WithClasses.class );
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
    private List<String> getProcessorOptions() {
        ProcessorOption processorOption = method.getAnnotation( ProcessorOption.class );

        if ( processorOption == null ) {
            processorOption = method.getMethod().getDeclaringClass().getAnnotation( ProcessorOption.class );
        }

        return processorOption != null ? Arrays.asList( asOptionString( processorOption ) )
            : Collections.<String>emptyList();
    }

    private String asOptionString(ProcessorOption processorOption) {
        return String.format( "-A%s=%s", processorOption.name(), processorOption.value() );
    }

    private Set<File> getSourceFiles(Collection<Class<?>> classes) {
        Set<File> sourceFiles = new HashSet<File>( classes.size() );

        for ( Class<?> clazz : classes ) {
            sourceFiles.add(
                new File(
                    sourceDir + File.separator + clazz.getName().replace( ".", File.separator )
                        + ".java"
                )
            );
        }

        return sourceFiles;
    }

    private CompilationResultHolder compile(Set<Class<?>> sourceClasses, List<String> processorOptions)
        throws Exception {
        CompilationRequest request = new CompilationRequest( sourceClasses, processorOptions );

        CompilationCache cache = COMPILATION_CACHE.get();
        if ( request.equals( cache.lastRequest ) ) {
            return cache.lastResult;
        }

        setupCompiler();
        DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<JavaFileObject>();
        StandardJavaFileManager fileManager = compiler.getStandardFileManager( null, null, null );

        Iterable<? extends JavaFileObject> compilationUnits =
            fileManager.getJavaFileObjectsFromFiles( getSourceFiles( sourceClasses ) );

        try {
            fileManager.setLocation( StandardLocation.CLASS_PATH, classPath );
            fileManager.setLocation( StandardLocation.CLASS_OUTPUT, Arrays.asList( new File( classOutputDir ) ) );
            fileManager.setLocation( StandardLocation.SOURCE_OUTPUT, Arrays.asList( new File( sourceOutputDir ) ) );
        }
        catch ( IOException e ) {
            throw new RuntimeException( e );
        }

        CompilationTask task =
            compiler.getTask( null, fileManager, diagnostics, processorOptions, null, compilationUnits );
        task.setProcessors( Arrays.asList( new MappingProcessor() ) );

        CompilationResultHolder resultHolder = new CompilationResultHolder( diagnostics, task.call() );

        cache.lastRequest = request;
        cache.lastResult = resultHolder;
        return resultHolder;
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

    private static class CompilationCache {
        private CompilationRequest lastRequest;
        private CompilationResultHolder lastResult;
    }

    /**
     * Represents the result of a compilation.
     */
    private static class CompilationResultHolder {
        private final DiagnosticCollector<JavaFileObject> diagnostics;
        private final boolean compilationSuccessful;

        public CompilationResultHolder(DiagnosticCollector<JavaFileObject> diagnostics, boolean compilationSuccessful) {
            this.diagnostics = diagnostics;
            this.compilationSuccessful = compilationSuccessful;
        }
    }

    /**
     * Represents a compilation task for a number of sources with given processor options.
     */
    private static class CompilationRequest {
        private final Set<Class<?>> sourceClasses;
        private final List<String> processorOptions;

        public CompilationRequest(Set<Class<?>> sourceClasses, List<String> processorOptions) {
            this.sourceClasses = sourceClasses;
            this.processorOptions = processorOptions;
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + ( ( processorOptions == null ) ? 0 : processorOptions.hashCode() );
            result = prime * result + ( ( sourceClasses == null ) ? 0 : sourceClasses.hashCode() );
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if ( this == obj ) {
                return true;
            }
            if ( obj == null ) {
                return false;
            }
            if ( getClass() != obj.getClass() ) {
                return false;
            }
            CompilationRequest other = (CompilationRequest) obj;

            return processorOptions.equals( other.processorOptions ) && sourceClasses.equals( other.sourceClasses );
        }
    }
}
