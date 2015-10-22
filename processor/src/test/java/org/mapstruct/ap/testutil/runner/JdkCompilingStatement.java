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
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

    private static final List<File> COMPILER_CLASSPATH_FILES = asFiles( COMPILER_CLASSPATH );

    JdkCompilingStatement(FrameworkMethod method, CompilationCache compilationCache) {
        super( method, compilationCache );
    }

    @Override
    protected CompilationOutcomeDescriptor compileWithSpecificCompiler(CompilationRequest compilationRequest,
                                                                       String sourceOutputDir,
                                                                       String classOutputDir) {
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

        CompilationTask task =
            compiler.getTask(
                null,
                fileManager,
                diagnostics,
                compilationRequest.getProcessorOptions(),
                null,
                compilationUnits );

        task.setProcessors( Arrays.asList( new MappingProcessor() ) );

        Boolean compilationSuccessful = task.call();

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
