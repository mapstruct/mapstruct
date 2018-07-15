/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.testutil.runner;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.processing.Processor;
import javax.tools.Diagnostic.Kind;
import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.JavaCompiler.CompilationTask;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.StandardLocation;
import javax.tools.ToolProvider;

import org.junit.runners.model.FrameworkMethod;
import org.mapstruct.ap.MappingProcessor;
import org.mapstruct.ap.testutil.compilation.model.CompilationOutcomeDescriptor;
import org.mapstruct.ap.testutil.compilation.model.DiagnosticDescriptor;

/**
 * Statement that uses the JDK compiler to compile.
 *
 * @author Andreas Gudian
 */
class JdkCompilingStatement extends CompilingStatement {

    private static final List<File> COMPILER_CLASSPATH_FILES = asFiles( TEST_COMPILATION_CLASSPATH );

    private static final ClassLoader DEFAULT_PROCESSOR_CLASSLOADER =
        new ModifiableURLClassLoader( new FilteringParentClassLoader( "org.mapstruct." ) )
                .withPaths( PROCESSOR_CLASSPATH );

    JdkCompilingStatement(FrameworkMethod method, CompilationCache compilationCache) {
        super( method, compilationCache );
    }

    @Override
    protected CompilationOutcomeDescriptor compileWithSpecificCompiler(CompilationRequest compilationRequest,
                                                                       String sourceOutputDir,
                                                                       String classOutputDir,
                                                                       String additionalCompilerClasspath) {
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<JavaFileObject>();
        StandardJavaFileManager fileManager = compiler.getStandardFileManager( null, null, null );

        Iterable<? extends JavaFileObject> compilationUnits =
            fileManager.getJavaFileObjectsFromFiles( getSourceFiles( compilationRequest.getSourceClasses() ) );

        try {
            fileManager.setLocation( StandardLocation.CLASS_PATH, COMPILER_CLASSPATH_FILES );
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

    private static List<File> asFiles(List<String> paths) {
        List<File> classpath = new ArrayList<File>();
        for ( String path : paths ) {
            classpath.add( new File( path ) );
        }

        return classpath;
    }

    /**
     * The JDK compiler only reports the first message of kind ERROR that is reported for one source file line, so we
     * filter out the surplus diagnostics. The input list is already sorted by file name and line number, with the order
     * for the diagnostics in the same line being kept at the order as given in the test.
     */
    @Override
    protected List<DiagnosticDescriptor> filterExpectedDiagnostics(List<DiagnosticDescriptor> expectedDiagnostics) {
        List<DiagnosticDescriptor> filtered = new ArrayList<DiagnosticDescriptor>( expectedDiagnostics.size() );

        DiagnosticDescriptor previous = null;
        for ( DiagnosticDescriptor diag : expectedDiagnostics ) {
            if ( diag.getKind() != Kind.ERROR
                || previous == null
                || !previous.getSourceFileName().equals( diag.getSourceFileName() )
                || !previous.getLine().equals( diag.getLine() ) ) {
                filtered.add( diag );
                previous = diag;
            }
        }

        return filtered;
    }

    @Override
    protected String getPathSuffix() {
        return "_jdk";
    }
}
