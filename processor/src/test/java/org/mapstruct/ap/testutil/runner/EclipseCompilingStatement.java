/**
 *  Copyright 2012-2017 Gunnar Morling (http://www.gunnarmorling.de/)
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

import java.io.File;
import java.util.List;
import java.util.Set;

import org.codehaus.plexus.compiler.CompilerConfiguration;
import org.codehaus.plexus.compiler.CompilerException;
import org.codehaus.plexus.compiler.CompilerResult;
import org.codehaus.plexus.logging.console.ConsoleLogger;
import org.eclipse.tycho.compiler.jdt.JDTCompiler;
import org.junit.runners.model.FrameworkMethod;
import org.mapstruct.ap.MappingProcessor;
import org.mapstruct.ap.testutil.compilation.model.CompilationOutcomeDescriptor;

/**
 * Statement that uses the Eclipse JDT compiler to compile.
 *
 * @author Andreas Gudian
 */
class EclipseCompilingStatement extends CompilingStatement {

    private static final List<String> ECLIPSE_COMPILER_CLASSPATH = buildEclipseCompilerClasspath();

    private static final ClassLoader DEFAULT_ECLIPSE_COMPILER_CLASSLOADER =
        new ModifiableURLClassLoader( newFilteringClassLoaderForEclipse() )
            .withPaths( ECLIPSE_COMPILER_CLASSPATH )
            .withPaths( PROCESSOR_CLASSPATH )
            .withOriginOf( ClassLoaderExecutor.class );

    EclipseCompilingStatement(FrameworkMethod method, CompilationCache compilationCache) {
        super( method, compilationCache );
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
            TEST_COMPILATION_CLASSPATH,
            getSourceFiles( compilationRequest.getSourceClasses() ),
            SOURCE_DIR,
            sourceOutputDir,
            classOutputDir );
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
            config.setShowWarnings( false );
            config.setSourceVersion( "1.8" );
            config.setTargetVersion( "1.8" );

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
    }

    private static List<String> buildEclipseCompilerClasspath() {
        String[] whitelist =
            new String[] {
                "tycho-compiler",
                "org.eclipse.jdt.",
                "plexus-compiler-api",
                "plexus-component-annotations" };

        return filterBootClassPath( whitelist );
    }

    @Override
    protected String getPathSuffix() {
        return "_eclipse";
    }
}
