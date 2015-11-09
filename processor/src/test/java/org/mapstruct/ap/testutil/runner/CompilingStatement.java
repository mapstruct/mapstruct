/**
 *  Copyright 2012-2015 Gunnar Morling (http://www.gunnarmorling.de/)
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

import java.io.ByteArrayOutputStream;
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
import java.util.Properties;
import java.util.Set;

import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.compilation.annotation.CompilationResult;
import org.mapstruct.ap.testutil.compilation.annotation.ExpectedCompilationOutcome;
import org.mapstruct.ap.testutil.compilation.annotation.ProcessorOption;
import org.mapstruct.ap.testutil.compilation.annotation.ProcessorOptions;
import org.mapstruct.ap.testutil.compilation.model.CompilationOutcomeDescriptor;
import org.mapstruct.ap.testutil.compilation.model.DiagnosticDescriptor;
import org.xml.sax.InputSource;

import com.google.common.collect.Lists;
import com.google.common.io.ByteStreams;
import com.puppycrawl.tools.checkstyle.Checker;
import com.puppycrawl.tools.checkstyle.ConfigurationLoader;
import com.puppycrawl.tools.checkstyle.DefaultLogger;
import com.puppycrawl.tools.checkstyle.PropertiesExpander;

import static org.fest.assertions.Assertions.assertThat;

/**
 * A JUnit4 statement that performs source generation using the annotation processor and compiles those sources.
 *
 * @author Andreas Gudian
 */
abstract class CompilingStatement extends Statement {

    private static final String TARGET_COMPILATION_TESTS = "/target/compilation-tests/";

    private static final String LINE_SEPARATOR = System.getProperty( "line.separator" );

    private static final DiagnosticDescriptorComparator COMPARATOR = new DiagnosticDescriptorComparator();

    protected static final String SOURCE_DIR = getBasePath() + "/src/test/java";

    protected static final List<String> COMPILER_CLASSPATH = buildCompilerClasspath();

    private final FrameworkMethod method;
    private final CompilationCache compilationCache;
    private Statement next;

    private String classOutputDir;
    private String sourceOutputDir;
    private CompilationRequest compilationRequest;

    CompilingStatement(FrameworkMethod method, CompilationCache compilationCache) {
        this.method = method;
        this.compilationCache = compilationCache;

        this.compilationRequest = new CompilationRequest( getTestClasses(), getProcessorOptions() );
    }

    void setNextStatement(Statement next) {
        this.next = next;
    }

    @Override
    public void evaluate() throws Throwable {
        generateMapperImplementation();

        GeneratedSource.setCompilingStatement( this );
        next.evaluate();
        GeneratedSource.clearCompilingStatement();
    }

    String getSourceOutputDir() {
        return compilationCache.getLastSourceOutputDir();
    }

    protected void setupDirectories() throws Exception {
        String compilationRoot = getBasePath()
            + TARGET_COMPILATION_TESTS
            + method.getDeclaringClass().getName()
            + "/" + method.getName()
            + getPathSuffix();

        classOutputDir = compilationRoot + "/classes";
        sourceOutputDir = compilationRoot + "/generated-sources";

        createOutputDirs();

        ( (ModifiableURLClassLoader) Thread.currentThread().getContextClassLoader() ).addOutputDir( classOutputDir );
    }

    protected abstract String getPathSuffix();

    private static List<String> buildCompilerClasspath() {
        String[] bootClasspath =
            System.getProperty( "java.class.path" ).split( System.getProperty( "path.separator" ) );
        String fs = System.getProperty( "file.separator" );
        String testClasses = "target" + fs + "test-classes";

        String[] whitelist =
            new String[] {
                "processor" + fs + "target",  // the processor itself
                "core" + fs + "target",  // MapStruct annotations in multi-module reactor build or IDE
                "org" + fs + "mapstruct" + fs + "mapstruct" + fs,  // MapStruct annotations in single module build
                "freemarker",
                "guava",
                "javax.inject",
                "spring-beans",
                "spring-context",
                "joda-time" };

        List<String> classpath = new ArrayList<String>();
        for ( String path : bootClasspath ) {
            if ( !path.contains( testClasses ) && isWhitelisted( path, whitelist ) ) {
                classpath.add( path );
            }
        }

        return classpath;
    }

    private static boolean isWhitelisted(String path, String[] whitelist) {
        for ( String whitelisted : whitelist ) {
            if ( path.contains( whitelisted ) ) {
                return true;
            }
        }
        return false;
    }

    protected void generateMapperImplementation() throws Exception {
        CompilationOutcomeDescriptor actualResult = compile();

        CompilationOutcomeDescriptor expectedResult =
            CompilationOutcomeDescriptor.forExpectedCompilationResult(
                method.getAnnotation( ExpectedCompilationOutcome.class )
            );

        if ( expectedResult.getCompilationResult() == CompilationResult.SUCCEEDED ) {
            assertThat( actualResult.getCompilationResult() ).describedAs(
                "Compilation failed. Diagnostics: " + actualResult.getDiagnostics()
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

        assertCheckstyleRules();
    }

    private void assertCheckstyleRules() throws Exception {
        if ( sourceOutputDir != null ) {
            Properties properties = new Properties();
            properties.put( "checkstyle.cache.file", classOutputDir + "/checkstyle.cache" );

            final Checker checker = new Checker();
            checker.setModuleClassLoader( Checker.class.getClassLoader() );
            checker.configure( ConfigurationLoader.loadConfiguration(
                new InputSource( getClass().getClassLoader().getResourceAsStream(
                    "checkstyle-for-generated-sources.xml" ) ),
                new PropertiesExpander( properties ),
                true ) );

            ByteArrayOutputStream errorStream = new ByteArrayOutputStream();
            checker.addListener( new DefaultLogger( ByteStreams.nullOutputStream(), true, errorStream, true ) );

            int errors = checker.process( findGeneratedFiles( new File( sourceOutputDir ) ) );
            if ( errors > 0 ) {
                String errorLog = errorStream.toString( "UTF-8" );
                assertThat( true ).describedAs( "Expected checkstyle compliant output, but got errors:\n" + errorLog )
                                  .isEqualTo( false );
            }
        }
    }

    private static List<File> findGeneratedFiles(File file) {
        final List<File> files = Lists.newLinkedList();

        if ( file.canRead() ) {
            if ( file.isDirectory() ) {
                for ( File element : file.listFiles() ) {
                    files.addAll( findGeneratedFiles( element ) );
                }
            }
            else if ( file.isFile() ) {
                files.add( file );
            }
        }
        return files;
    }

    private void assertDiagnostics(List<DiagnosticDescriptor> actualDiagnostics,
                                   List<DiagnosticDescriptor> expectedDiagnostics) {

        Collections.sort( actualDiagnostics, COMPARATOR );
        Collections.sort( expectedDiagnostics, COMPARATOR );
        expectedDiagnostics = filterExpectedDiagnostics( expectedDiagnostics );

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
            ).matches( "(?ms).*" + expected.getMessage() + ".*" );
        }
    }

    /**
     * @param expectedDiagnostics expected diagnostics
     * @return a possibly filtered list of expected diagnostics
     */
    protected List<DiagnosticDescriptor> filterExpectedDiagnostics(List<DiagnosticDescriptor> expectedDiagnostics) {
        return expectedDiagnostics;
    }

    /**
     * Returns the classes to be compiled for this test.
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
     * @return A list containing the processor options to be used for this test
     */
    private List<String> getProcessorOptions() {
        List<ProcessorOption> processorOptions =
            getProcessorOptions(
                method.getAnnotation( ProcessorOptions.class ),
                method.getAnnotation( ProcessorOption.class ) );

        if ( processorOptions.isEmpty() ) {
            processorOptions =
                getProcessorOptions(
                    method.getMethod().getDeclaringClass().getAnnotation( ProcessorOptions.class ),
                    method.getMethod().getDeclaringClass().getAnnotation( ProcessorOption.class ) );
        }

        List<String> result = new ArrayList<String>( processorOptions.size() );
        for ( ProcessorOption option : processorOptions ) {
            result.add( asOptionString( option ) );
        }

        return result;
    }

    private List<ProcessorOption> getProcessorOptions(ProcessorOptions options, ProcessorOption option) {
        if ( options != null ) {
            return Arrays.asList( options.value() );
        }
        else if ( option != null ) {
            return Arrays.asList( option );
        }

        return Collections.emptyList();
    }

    private String asOptionString(ProcessorOption processorOption) {
        return String.format( "-A%s=%s", processorOption.name(), processorOption.value() );
    }

    protected Set<File> getSourceFiles(Collection<Class<?>> classes) {
        Set<File> sourceFiles = new HashSet<File>( classes.size() );

        for ( Class<?> clazz : classes ) {
            sourceFiles.add(
                new File(
                    SOURCE_DIR + File.separator + clazz.getName().replace( ".", File.separator )
                        + ".java"
                )
            );
        }

        return sourceFiles;
    }

    private CompilationOutcomeDescriptor compile()
        throws Exception {

        if ( !needsRecompilation() ) {
            return compilationCache.getLastResult();
        }

        setupDirectories();
        compilationCache.setLastSourceOutputDir( sourceOutputDir );

        CompilationOutcomeDescriptor resultHolder =
            compileWithSpecificCompiler( compilationRequest, sourceOutputDir, classOutputDir );

        compilationCache.update( compilationRequest, resultHolder );
        return resultHolder;
    }

    protected abstract CompilationOutcomeDescriptor compileWithSpecificCompiler(
                                                                                CompilationRequest compilationRequest,
                                                                                String sourceOutputDir,
                                                                                String classOutputDir);

    boolean needsRecompilation() {
        return !compilationRequest.equals( compilationCache.getLastRequest() );
    }

    private static String getBasePath() {
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

            return o1.getKind().compareTo( o2.getKind() );
        }
    }
}
