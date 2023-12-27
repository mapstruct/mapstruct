/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.testutil.runner;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import com.puppycrawl.tools.checkstyle.Checker;
import com.puppycrawl.tools.checkstyle.ConfigurationLoader;
import com.puppycrawl.tools.checkstyle.DefaultLogger;
import com.puppycrawl.tools.checkstyle.PropertiesExpander;
import com.puppycrawl.tools.checkstyle.api.AutomaticBean;
import org.apache.commons.io.output.NullOutputStream;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.WithServiceImplementation;
import org.mapstruct.ap.testutil.WithTestDependency;
import org.mapstruct.ap.testutil.compilation.annotation.CompilationResult;
import org.mapstruct.ap.testutil.compilation.annotation.DisableCheckstyle;
import org.mapstruct.ap.testutil.compilation.annotation.ExpectedCompilationOutcome;
import org.mapstruct.ap.testutil.compilation.annotation.ExpectedNote;
import org.mapstruct.ap.testutil.compilation.annotation.ProcessorOption;
import org.mapstruct.ap.testutil.compilation.model.CompilationOutcomeDescriptor;
import org.mapstruct.ap.testutil.compilation.model.DiagnosticDescriptor;
import org.xml.sax.InputSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.platform.commons.support.AnnotationSupport.findAnnotation;
import static org.junit.platform.commons.support.AnnotationSupport.findRepeatableAnnotations;

/**
 * A JUnit Jupiter Extension that performs source generation using the annotation processor and compiles those sources.
 *
 * @author Andreas Gudian
 * @author Filip Hrisafov
 */
abstract class CompilingExtension implements BeforeEachCallback {

    static final ExtensionContext.Namespace NAMESPACE = ExtensionContext.Namespace.create( new Object() );

    private static final String TARGET_COMPILATION_TESTS = "/target/compilation-tests/";

    private static final String LINE_SEPARATOR = System.lineSeparator( );

    private static final DiagnosticDescriptorComparator COMPARATOR = new DiagnosticDescriptorComparator();

    protected static final String SOURCE_DIR = getBasePath() + "/src/test/java";

    protected static final List<String> TEST_COMPILATION_CLASSPATH = buildTestCompilationClasspath();

    protected static final List<String> PROCESSOR_CLASSPATH = buildProcessorClasspath();

    private String classOutputDir;
    private String sourceOutputDir;
    private String additionalCompilerClasspath;
    private final Compiler compiler;

    protected CompilingExtension(Compiler compiler) {
        this.compiler = compiler;
    }

    protected void setupDirectories(Method testMethod, Class<?> testClass) {
        String compilationRoot = getBasePath()
            + TARGET_COMPILATION_TESTS
            + testClass.getName()
            + "/" + testMethod.getName()
            + getPathSuffix();

        classOutputDir = compilationRoot + "/classes";
        sourceOutputDir = compilationRoot + "/generated-sources";
        additionalCompilerClasspath = compilationRoot + "/compiler";

        createOutputDirs();

        ( (ModifiableURLClassLoader) Thread.currentThread().getContextClassLoader() ).withPath( classOutputDir );
    }

    protected String getPathSuffix() {
        return "_" + compiler.name().toLowerCase();
    }

    /**
     * Build the default test compilation classpath
     * needed for compiling the generated sources once the processor has run.
     */
    private static List<String> buildTestCompilationClasspath() {
        Collection<String> whitelist = Arrays.asList(
                // MapStruct annotations in multi-module reactor build or IDE
                "core" + File.separator + "target",
                // MapStruct annotations in single module build
                "org" + File.separator + "mapstruct" + File.separator + "mapstruct" + File.separator,
                "guava"
        );

        return filterBootClassPath( whitelist );
    }

    /**
     * Build the annotation processor classpath.
     * i.e. the classpath that is needed to run the annotation processor with its own dependencies only.
     * The optional dependencies are not needed in this classpath.
     */
    private static List<String> buildProcessorClasspath() {
        Collection<String> whitelist = Arrays.asList(
                "processor" + File.separator + "target",  // the processor itself,
                "freemarker",
                "gem-api"
        );

        return filterBootClassPath( whitelist );
    }

    protected static List<String> filterBootClassPath(Collection<String> whitelist) {
        String[] bootClasspath =
            System.getProperty( "java.class.path" ).split( File.pathSeparator );
        String testClasses = "target" + File.separator + "test-classes";

        List<String> classpath = new ArrayList<>();
        for ( String path : bootClasspath ) {
            if ( !path.contains( testClasses ) && isWhitelisted( path, whitelist ) ) {
                classpath.add( path );
            }
        }

        return classpath;
    }

    private static boolean isWhitelisted(String path, Collection<String> whitelist) {
        return whitelist.stream().anyMatch( path::contains );
    }

    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
        CompilationOutcomeDescriptor actualResult = compile( context );
        assertResult( actualResult, context );
    }

    private void assertResult(CompilationOutcomeDescriptor actualResult, ExtensionContext context) throws Exception {
        Method testMethod = context.getRequiredTestMethod();
        Class<?> testClass = context.getRequiredTestClass();

        CompilationOutcomeDescriptor expectedResult =
            CompilationOutcomeDescriptor.forExpectedCompilationResult(
                findAnnotation( testMethod, ExpectedCompilationOutcome.class ).orElse( null ),
                findAnnotation( testMethod, ExpectedNote.ExpectedNotes.class ).orElse( null ),
                findAnnotation( testMethod, ExpectedNote.class ).orElse( null )
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
        assertNotes( actualResult.getNotes(), expectedResult.getNotes() );

        if ( !findAnnotation( testClass, DisableCheckstyle.class ).isPresent() && !skipCheckstyleBySystemProperty() ) {
            assertCheckstyleRules();
        }
    }

    private static boolean skipCheckstyleBySystemProperty() {
        return Boolean.parseBoolean( System.getProperty( "checkstyle.skip" ) );
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
                ConfigurationLoader.IgnoredModulesOptions.OMIT
            ) );

            ByteArrayOutputStream errorStream = new ByteArrayOutputStream();
            checker.addListener(
                new DefaultLogger(
                    NullOutputStream.NULL_OUTPUT_STREAM,
                    AutomaticBean.OutputStreamOptions.CLOSE,
                    errorStream,
                    AutomaticBean.OutputStreamOptions.CLOSE
                )
            );

            int errors = checker.process( findGeneratedFiles( new File( sourceOutputDir ) ) );
            if ( errors > 0 ) {
                String errorLog = errorStream.toString( "UTF-8" );
                assertThat( true ).describedAs( "Expected checkstyle compliant output, but got errors:\n" + errorLog )
                                  .isEqualTo( false );
            }
        }
    }

    private static List<File> findGeneratedFiles(File file) {
        final List<File> files = new LinkedList<>();

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

    private void assertNotes(List<String> actualNotes, List<String> expectedNotes) {
        List<String> expectedNotesRemaining = new ArrayList<>( expectedNotes );
        Iterator<String> expectedNotesIterator = expectedNotesRemaining.iterator();
        if ( expectedNotesIterator.hasNext() ) {
            String expectedNoteRegexp = expectedNotesIterator.next();
            for ( String actualNote : actualNotes ) {
                if ( actualNote.matches( expectedNoteRegexp ) ) {
                    expectedNotesIterator.remove();
                    if ( expectedNotesIterator.hasNext() ) {
                        expectedNoteRegexp = expectedNotesIterator.next();
                    }
                    else {
                        break;
                    }
                }
            }
        }

        assertThat( expectedNotesRemaining )
            .describedAs( "There are unmatched notes: " +
                String.join( LINE_SEPARATOR, expectedNotesRemaining ) )
            .isEmpty();
    }

    private void assertDiagnostics(List<DiagnosticDescriptor> actualDiagnostics,
                                   List<DiagnosticDescriptor> expectedDiagnostics) {

        actualDiagnostics.sort( COMPARATOR );
        expectedDiagnostics.sort( COMPARATOR );
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
            if ( expected.getLine() != null && expected.getAlternativeLine() != null ) {
                assertThat( actual.getLine() ).isIn( expected.getLine(), expected.getAlternativeLine() );
            }
            else if ( expected.getLine() != null ) {
                assertThat( actual.getLine() ).as( actual.getMessage() ).isEqualTo( expected.getLine() );
            }
            assertThat( actual.getKind() )
                .as( actual.getMessage() )
                .isEqualTo( expected.getKind() );
            if ( expected.getMessage() != null && !expected.getMessage().isEmpty() ) {
                assertThat( actual.getMessage() ).describedAs(
                    String.format(
                        "Unexpected message for diagnostic %s:%s %s",
                        actual.getSourceFileName(),
                        actual.getLine(),
                        actual.getKind()
                    )
                ).isEqualTo( expected.getMessage() );
            }
            else {
                assertThat( actual.getMessage() ).describedAs(
                    String.format(
                        "Unexpected message for diagnostic %s:%s %s",
                        actual.getSourceFileName(),
                        actual.getLine(),
                        actual.getKind()
                    )
                ).matches( "(?ms).*" + expected.getMessageRegex() + ".*" );
            }
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
    private Set<Class<?>> getTestClasses(Method testMethod, Class<?> testClass) {
        Set<Class<?>> testClasses = new HashSet<>();

        findAnnotation( testMethod, WithClasses.class )
            .ifPresent( withClasses -> testClasses.addAll( Arrays.asList( withClasses.value() ) ) );

        findAnnotation( testClass, WithClasses.class )
            .ifPresent( withClasses -> testClasses.addAll( Arrays.asList( withClasses.value() ) ) );

        if ( testClasses.isEmpty() ) {
            throw new IllegalStateException(
                "The classes to be compiled during the test must be specified via @WithClasses."
            );
        }

        return testClasses;
    }

    /**
     * Returns the resources to be compiled for this test.
     *
     * @return A map containing the package were to look for a resource (key) and the resource (value) to be compiled
     * for this test
     */
    private Map<Class<?>, Class<?>> getServices(Method testMethod, Class<?> testClass) {
        Map<Class<?>, Class<?>> services = new HashMap<>();

        addServices( services, findRepeatableAnnotations( testMethod, WithServiceImplementation.class ) );

        addServices( services, findRepeatableAnnotations( testClass, WithServiceImplementation.class ) );

        return services;
    }

    private Collection<String> getAdditionalTestDependencies(Method testMethod, Class<?> testClass) {
        Collection<String> testDependencies = new HashSet<>();
        findRepeatableAnnotations( testMethod, WithTestDependency.class )
            .forEach( annotation ->  Collections.addAll( testDependencies, annotation.value() ) );

        findRepeatableAnnotations( testClass, WithTestDependency.class )
            .forEach( annotation ->  Collections.addAll( testDependencies, annotation.value() ) );

        return testDependencies;
    }

    private void addServices(Map<Class<?>, Class<?>> services, List<WithServiceImplementation> withImplementations) {
        for ( WithServiceImplementation withImplementation : withImplementations ) {
            addService( services, withImplementation );
        }
    }

    private void addService(Map<Class<?>, Class<?>> services, WithServiceImplementation annoation) {
        if ( annoation == null ) {
            return;
        }

        Class<?> provides = annoation.provides();
        Class<?> implementor = annoation.value();
        if ( provides == Object.class ) {
            Class<?>[] implemented = implementor.getInterfaces();
            if ( implemented.length != 1 ) {
                throw new IllegalArgumentException(
                    "The class " + implementor.getName()
                        + " either needs to implement exactly one interface, or \"provides\" needs to be specified"
                        + " as well in the annotation " + WithServiceImplementation.class.getSimpleName() + "." );
            }

            provides = implemented[0];
        }

        services.put( provides, implementor );
    }

    /**
     * Returns the processor options to be used this test.
     *
     * @return A list containing the processor options to be used for this test
     */
    private List<String> getProcessorOptions(Method testMethod, Class<?> testClass) {
        List<ProcessorOption> processorOptions = findRepeatableAnnotations( testMethod, ProcessorOption.class );

        if ( processorOptions.isEmpty() ) {
            processorOptions = findRepeatableAnnotations( testClass, ProcessorOption.class );
        }

        List<String> result = new ArrayList<>( processorOptions.size() );
        for ( ProcessorOption option : processorOptions ) {
            result.add( asOptionString( option ) );
        }

        // Add all debugging info to class files
        result.add( "-g:source,lines,vars" );

        return result;
    }

    private String asOptionString(ProcessorOption processorOption) {
        return String.format( "-A%s=%s", processorOption.name(), processorOption.value() );
    }

    protected static Set<File> getSourceFiles(Collection<Class<?>> classes) {
        Set<File> sourceFiles = new HashSet<>( classes.size() );

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

    private CompilationOutcomeDescriptor compile(ExtensionContext context) {
        Method testMethod = context.getRequiredTestMethod();
        Class<?> testClass = context.getRequiredTestClass();

        CompilationRequest compilationRequest = new CompilationRequest(
            compiler,
            getTestClasses( testMethod, testClass ),
            getServices( testMethod, testClass ),
            getProcessorOptions( testMethod, testClass ),
            getAdditionalTestDependencies( testMethod, testClass )
        );

        ExtensionContext.Store rootStore = context.getRoot().getStore( NAMESPACE );

        // We need to put the compilation request in the store, so the GeneratedSource can use it
        context.getStore( NAMESPACE ).put( context.getUniqueId() + "-compilationRequest", compilationRequest );
        CompilationCache compilationCache = rootStore
            .getOrComputeIfAbsent( compilationRequest, request -> new CompilationCache(), CompilationCache.class );

        if ( !needsRecompilation( compilationRequest, compilationCache ) ) {
            return compilationCache.getLastResult();
        }

        setupDirectories( testMethod, testClass );
        compilationCache.setLastSourceOutputDir( sourceOutputDir );

        boolean needsAdditionalCompilerClasspath = prepareServices( compilationRequest );
        CompilationOutcomeDescriptor resultHolder;

        resultHolder = compileWithSpecificCompiler(
            compilationRequest,
            sourceOutputDir,
            classOutputDir,
            needsAdditionalCompilerClasspath ? additionalCompilerClasspath : null );

        compilationCache.update( compilationRequest, resultHolder );
        return resultHolder;
    }

    protected Object loadAndInstantiate(ClassLoader processorClassloader, Class<?> clazz) {
        try {
            return processorClassloader.loadClass( clazz.getName() ).newInstance();
        }
        catch ( Exception e ) {
            throw new RuntimeException( e );
        }
    }

    protected abstract CompilationOutcomeDescriptor compileWithSpecificCompiler(
                                                                                CompilationRequest compilationRequest,
                                                                                String sourceOutputDir,
                                                                                String classOutputDir,
                                                                                String additionalCompilerClasspath);

    boolean needsRecompilation(CompilationRequest compilationRequest, CompilationCache compilationCache) {
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

        directory = new File( additionalCompilerClasspath );
        deleteDirectory( directory );
        directory.mkdirs();
    }

    private void deleteDirectory(File path) {
        if ( path.exists() ) {
            File[] files = path.listFiles();
            for ( File file : files ) {
                if ( file.isDirectory() ) {
                    deleteDirectory( file );
                }
                else {
                    file.delete();
                }
            }
        }
        path.delete();
    }

    private boolean prepareServices(CompilationRequest compilationRequest) {
        if ( !compilationRequest.getServices().isEmpty() ) {
            String servicesDir =
                additionalCompilerClasspath + File.separator + "META-INF" + File.separator + "services";
            File directory = new File( servicesDir );
            deleteDirectory( directory );
            directory.mkdirs();
            for ( Map.Entry<Class<?>, Class<?>> serviceEntry : compilationRequest.getServices().entrySet() ) {
                try {
                    File file = new File( servicesDir + File.separator + serviceEntry.getKey().getName() );
                    FileWriter fileWriter = new FileWriter( file );
                    fileWriter.append( serviceEntry.getValue().getName() ).append( "\n" );
                    fileWriter.flush();
                    fileWriter.close();
                }
                catch ( IOException e ) {
                    throw new RuntimeException( e );
                }
            }

            return true;
        }

        return false;
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
            result = o1.getLine().compareTo( o2.getLine() );
            if ( result != 0 ) {
                return result;
            }

            return o1.getKind().compareTo( o2.getKind() );
        }
    }
}
