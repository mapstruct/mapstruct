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

import java.io.File;

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

    EclipseCompilingStatement(FrameworkMethod method, CompilationCache compilationCache) {
        super( method, compilationCache );
    }

    @Override
    protected CompilationOutcomeDescriptor compileWithSpecificCompiler(CompilationRequest compilationRequest,
                                                                       String sourceOutputDir,
                                                                       String classOutputDir) {
        JDTCompiler compiler = new JDTCompiler();
        compiler.enableLogging( new ConsoleLogger( 5, "JDT-Compiler" ) );

        CompilerConfiguration config = new CompilerConfiguration();

        config.setClasspathEntries( COMPILER_CLASSPATH );
        config.setOutputLocation( classOutputDir );
        config.setGeneratedSourcesDirectory( new File( sourceOutputDir ) );
        config.setAnnotationProcessors( new String[] { MappingProcessor.class.getName() } );
        config.setSourceFiles( getSourceFiles( compilationRequest.getSourceClasses() ) );
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
            SOURCE_DIR,
            compilerResult );
    }

    @Override
    protected String getPathSuffix() {
        return "_eclipse";
    }
}
