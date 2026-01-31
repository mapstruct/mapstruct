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
import javax.annotation.processing.Processor;
import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.JavaCompiler.CompilationTask;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.StandardLocation;
import javax.tools.ToolProvider;

import org.mapstruct.ap.MappingProcessor;
import org.mapstruct.ap.testutil.compilation.model.CompilationOutcomeDescriptor;

/**
 * Extension that uses the JDK compiler to compile.
 *
 * @author Andreas Gudian
 * @author Filip Hrisafov
 */
class JdkCompilingExtension extends CompilingExtension {

    private static final List<File> COMPILER_CLASSPATH_FILES = asFiles( TEST_COMPILATION_CLASSPATH );

    private static final ClassLoader DEFAULT_PROCESSOR_CLASSLOADER =
        new ModifiableURLClassLoader( newFilteringClassLoaderForJdk() )
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
            fileManager.setLocation(
                StandardLocation.CLASS_PATH,
                getCompilerClasspathFiles( compilationRequest, classOutputDir )
            );
            fileManager.setLocation( StandardLocation.CLASS_OUTPUT, Arrays.asList( new File( classOutputDir ) ) );
            fileManager.setLocation( StandardLocation.SOURCE_OUTPUT, Arrays.asList( new File( sourceOutputDir ) ) );
        }
        catch ( IOException e ) {
            throw new RuntimeException( e );
        }

        Collection<String> processorClassPaths = getProcessorClasspathDependencies(
            compilationRequest,
            additionalCompilerClasspath
        );
        ClassLoader processorClassloader;
        if ( processorClassPaths.isEmpty() ) {
            processorClassloader = DEFAULT_PROCESSOR_CLASSLOADER;
        }
        else {
            processorClassloader = new ModifiableURLClassLoader(
                newFilteringClassLoaderForJdk()
                    .hidingClasses( compilationRequest.getServices().values() )
            )
                    .withPaths( PROCESSOR_CLASSPATH )
                    .withPaths( processorClassPaths )
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

    private static List<File> getCompilerClasspathFiles(CompilationRequest request, String classOutputDir) {
        Collection<String> testDependencies = request.getTestDependencies();
        Collection<String> processorDependencies = request.getProcessorDependencies();
        Collection<String> kotlinSources = request.getKotlinSources();
        if ( testDependencies.isEmpty() && processorDependencies.isEmpty() && kotlinSources.isEmpty() ) {
            return COMPILER_CLASSPATH_FILES;
        }

        List<File> compilerClasspathFiles = new ArrayList<>(
            COMPILER_CLASSPATH_FILES.size() + testDependencies.size() + processorDependencies.size() + 1 );

        compilerClasspathFiles.addAll( COMPILER_CLASSPATH_FILES );
        for ( String testDependencyPath : filterBootClassPath( testDependencies ) ) {
            compilerClasspathFiles.add( new File( testDependencyPath ) );
        }

        if ( !kotlinSources.isEmpty() ) {
            compilerClasspathFiles.add( new File( classOutputDir ) );
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

    private static FilteringParentClassLoader newFilteringClassLoaderForJdk() {
        return new FilteringParentClassLoader(
            "kotlin.",
            // reload mapstruct processor classes
            "org.mapstruct.ap.internal.",
            "org.mapstruct.ap.spi.",
            "org.mapstruct.ap.MappingProcessor"
        );
    }
}
