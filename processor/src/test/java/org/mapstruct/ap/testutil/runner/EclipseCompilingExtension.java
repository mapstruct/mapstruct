/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.testutil.runner;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import javax.lang.model.SourceVersion;

import org.codehaus.plexus.compiler.CompilerConfiguration;
import org.codehaus.plexus.compiler.CompilerException;
import org.codehaus.plexus.compiler.CompilerResult;
import org.codehaus.plexus.logging.console.ConsoleLogger;
import org.eclipse.tycho.compiler.jdt.JDTCompiler;
import org.mapstruct.ap.MappingProcessor;
import org.mapstruct.ap.testutil.compilation.model.CompilationOutcomeDescriptor;

/**
 * Extension that uses the Eclipse JDT compiler to compile.
 *
 * @author Andreas Gudian
 * @author Filip Hrisafov
 */
class EclipseCompilingExtension extends CompilingExtension {

    private static final List<String> ECLIPSE_COMPILER_CLASSPATH = buildEclipseCompilerClasspath();

    private static final ClassLoader DEFAULT_ECLIPSE_COMPILER_CLASSLOADER =
        new ModifiableURLClassLoader( newFilteringClassLoaderForEclipse() )
            .withPaths( ECLIPSE_COMPILER_CLASSPATH )
            .withPaths( PROCESSOR_CLASSPATH )
            .withOriginOf( ClassLoaderExecutor.class );

    EclipseCompilingExtension() {
        super( Compiler.ECLIPSE );
    }

    @Override
    protected CompilationOutcomeDescriptor compileWithSpecificCompiler(CompilationRequest compilationRequest,
                                                                       String sourceOutputDir,
                                                                       String classOutputDir,
                                                                       String additionalCompilerClasspath) {
        ClassLoader compilerClassloader;
        if ( additionalCompilerClasspath == null ) {
            compilerClassloader = DEFAULT_ECLIPSE_COMPILER_CLASSLOADER;
        }
        else {
            ModifiableURLClassLoader loader = new ModifiableURLClassLoader(
                newFilteringClassLoaderForEclipse()
                .hidingClasses( compilationRequest.getServices().values() ) );

            compilerClassloader = loader.withPaths( ECLIPSE_COMPILER_CLASSPATH )
                  .withPaths( PROCESSOR_CLASSPATH )
                  .withOriginOf( ClassLoaderExecutor.class )
                  .withPath( additionalCompilerClasspath )
                  .withOriginsOf( compilationRequest.getServices().values() );
        }

        ClassLoaderHelper clHelper =
            (ClassLoaderHelper) loadAndInstantiate( compilerClassloader, ClassLoaderExecutor.class );

        return clHelper.compileInOtherClassloader(
            compilationRequest,
            getTestCompilationClasspath( compilationRequest, classOutputDir ),
            getSourceFiles( compilationRequest.getSourceClasses() ),
            SOURCE_DIR,
            sourceOutputDir,
            classOutputDir );
    }

    private static List<String> getTestCompilationClasspath(CompilationRequest request, String classOutputDir) {
        Collection<String> testDependencies = request.getTestDependencies();
        if ( testDependencies.isEmpty() && request.getKotlinSources().isEmpty() ) {
            return TEST_COMPILATION_CLASSPATH;
        }

        List<String> testCompilationPaths = new ArrayList<>(
            TEST_COMPILATION_CLASSPATH.size() + testDependencies.size() + 1 );

        testCompilationPaths.addAll( TEST_COMPILATION_CLASSPATH );
        testCompilationPaths.addAll( filterBootClassPath( testDependencies ) );
        if ( !request.getKotlinSources().isEmpty() ) {
            testCompilationPaths.add( classOutputDir );
        }
        return testCompilationPaths;
    }

    private static FilteringParentClassLoader newFilteringClassLoaderForEclipse() {
        return new FilteringParentClassLoader(
            // reload eclipse compiler classes
            "org.eclipse.",
            // reload mapstruct processor classes
            "org.mapstruct.ap.internal.",
            "org.mapstruct.ap.spi.",
            "org.mapstruct.ap.MappingProcessor")
        .hidingClass( ClassLoaderExecutor.class );
    }

    public interface ClassLoaderHelper {
        CompilationOutcomeDescriptor compileInOtherClassloader(CompilationRequest compilationRequest,
                                                               List<String> testCompilationClasspath,
                                                               Set<File> sourceFiles,
                                                               String sourceDir,
                                                               String sourceOutputDir,
                                                               String classOutputDir);
    }

    public static final class ClassLoaderExecutor implements ClassLoaderHelper {
        @Override
        public CompilationOutcomeDescriptor compileInOtherClassloader(CompilationRequest compilationRequest,
                                                                      List<String> testCompilationClasspath,
                                                                      Set<File> sourceFiles,
                                                                      String sourceDir,
                                                                      String sourceOutputDir,
                                                                      String classOutputDir) {
            JDTCompiler compiler = new JDTCompiler();
            compiler.enableLogging( new ConsoleLogger( 5, "JDT-Compiler" ) );

            CompilerConfiguration config = new CompilerConfiguration();

            config.setClasspathEntries( testCompilationClasspath );
            config.setOutputLocation( classOutputDir );
            config.setGeneratedSourcesDirectory( new File( sourceOutputDir ) );
            config.setAnnotationProcessors( new String[] { MappingProcessor.class.getName() } );
            config.setSourceFiles( sourceFiles );
            String version = getSourceVersion();
            config.setShowWarnings( false );
            config.setSourceVersion( version );
            config.setTargetVersion( version );

            for ( String option : compilationRequest.getProcessorOptions() ) {
                config.addCompilerCustomArgument( option, null );
            }

            CompilerResult compilerResult;
            try {
                compilerResult = compiler.performCompile( config );
            }
            catch ( CompilerException e ) {
                throw new RuntimeException( e );
            }

            return CompilationOutcomeDescriptor.forResult(
                sourceDir,
                compilerResult );
        }

        private static String getSourceVersion() {
            SourceVersion latest = SourceVersion.latest();
            if ( latest == SourceVersion.RELEASE_8 ) {
                return "1.8";
            }
            return "11";
        }

    }

    private static List<String> buildEclipseCompilerClasspath() {
        Collection<String> whitelist = Arrays.asList(
                "tycho-compiler",
                "ecj",
                "plexus-compiler-api",
                "plexus-component-annotations"
        );

        return filterBootClassPath( whitelist );
    }
}
