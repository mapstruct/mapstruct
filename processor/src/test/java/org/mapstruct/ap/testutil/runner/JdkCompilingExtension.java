/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.testutil.runner;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import javax.annotation.processing.Processor;
import javax.tools.Diagnostic.Kind;
import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.JavaCompiler.CompilationTask;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.StandardLocation;
import javax.tools.ToolProvider;

import org.junit.jupiter.api.condition.JRE;
import org.mapstruct.ap.MappingProcessor;
import org.mapstruct.ap.testutil.compilation.model.CompilationOutcomeDescriptor;
import org.mapstruct.ap.testutil.compilation.model.DiagnosticDescriptor;

/**
 * Extension that uses the JDK compiler to compile.
 *
 * @author Andreas Gudian
 * @author Filip Hrisafov
 */
class JdkCompilingExtension extends CompilingExtension {

    private static final List<File> COMPILER_CLASSPATH_FILES = asFiles( TEST_COMPILATION_CLASSPATH );

    private static final ClassLoader DEFAULT_PROCESSOR_CLASSLOADER =
        new ModifiableURLClassLoader( new FilteringParentClassLoader( "org.mapstruct." ) )
                .withPaths( PROCESSOR_CLASSPATH );

    JdkCompilingExtension() {
        super( Compiler.JDK );
    }

    @Override
    protected CompilationOutcomeDescriptor compileWithSpecificCompiler(CompilationRequest compilationRequest,
                                                                       String sourceOutputDir,
                                                                       String classOutputDir,
                                                                       String additionalCompilerClasspath) {
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<>();
        StandardJavaFileManager fileManager = compiler.getStandardFileManager( null, null, StandardCharsets.UTF_8 );

        Iterable<? extends JavaFileObject> compilationUnits =
            fileManager.getJavaFileObjectsFromFiles( getSourceFiles( compilationRequest.getSourceClasses() ) );

        try {
            fileManager.setLocation( StandardLocation.CLASS_PATH, getCompilerClasspathFiles( compilationRequest ) );
            fileManager.setLocation( StandardLocation.CLASS_OUTPUT, Arrays.asList( new File( classOutputDir ) ) );
            fileManager.setLocation( StandardLocation.SOURCE_OUTPUT, Arrays.asList( new File( sourceOutputDir ) ) );
        }
        catch ( IOException e ) {
            throw new RuntimeException( e );
        }

        ClassLoader processorClassloader;
        if ( additionalCompilerClasspath == null ) {
            processorClassloader = DEFAULT_PROCESSOR_CLASSLOADER;
        }
        else {
            processorClassloader = new ModifiableURLClassLoader(
                new FilteringParentClassLoader( "org.mapstruct." ) )
                    .withPaths( PROCESSOR_CLASSPATH )
                    .withPath( additionalCompilerClasspath )
                    .withOriginsOf( compilationRequest.getServices().values() );
        }

        CompilationTask task =
            compiler.getTask(
                null,
                fileManager,
                diagnostics,
                compilationRequest.getProcessorOptions(),
                null,
                compilationUnits );

        task.setProcessors(
            Arrays.asList( (Processor) loadAndInstantiate( processorClassloader, MappingProcessor.class ) ) );

        boolean compilationSuccessful = task.call();

        return CompilationOutcomeDescriptor.forResult(
            SOURCE_DIR,
            compilationSuccessful,
            diagnostics.getDiagnostics() );
    }

    private static List<File> getCompilerClasspathFiles(CompilationRequest request) {
        Collection<String> testDependencies = request.getTestDependencies();
        if ( testDependencies.isEmpty() ) {
            return COMPILER_CLASSPATH_FILES;
        }

        List<File> compilerClasspathFiles = new ArrayList<>(
            COMPILER_CLASSPATH_FILES.size() + testDependencies.size() );

        compilerClasspathFiles.addAll( COMPILER_CLASSPATH_FILES );
        for ( String testDependencyPath : filterBootClassPath( testDependencies ) ) {
            compilerClasspathFiles.add( new File( testDependencyPath ) );
        }

        return compilerClasspathFiles;
    }

    private static List<File> asFiles(List<String> paths) {
        List<File> classpath = new ArrayList<>();
        for ( String path : paths ) {
            classpath.add( new File( path ) );
        }

        return classpath;
    }

    /**
     * The JDK 8 compiler needs some special treatment for the diagnostics.
     * See comment in the function.
     */
    @Override
    protected List<DiagnosticDescriptor> filterExpectedDiagnostics(List<DiagnosticDescriptor> expectedDiagnostics) {
        if ( JRE.currentVersion() != JRE.JAVA_8 ) {
            // The JDK 8+ compilers report all ERROR diagnostics properly. Also when there are multiple per line.
            return expectedDiagnostics;
        }
        List<DiagnosticDescriptor> filtered = new ArrayList<>(expectedDiagnostics.size());

        // The JDK 8 compiler only reports the first message of kind ERROR that is reported for one source file line,
        // so we filter out the surplus diagnostics. The input list is already sorted by file name and line number,
        // with the order for the diagnostics in the same line being kept at the order as given in the test.
        DiagnosticDescriptor previous = null;
        for ( DiagnosticDescriptor diag : expectedDiagnostics ) {
            if ( diag.getKind() != Kind.ERROR
                || previous == null
                || !previous.getSourceFileName().equals( diag.getSourceFileName() )
                || !Objects.equals( previous.getLine(), diag.getLine() ) ) {
                filtered.add( diag );
                previous = diag;
            }
        }

        return filtered;
    }

}
