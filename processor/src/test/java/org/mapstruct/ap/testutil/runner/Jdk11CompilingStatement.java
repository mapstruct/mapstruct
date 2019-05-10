/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.testutil.runner;

import java.util.List;

import org.junit.runners.model.FrameworkMethod;
import org.mapstruct.ap.testutil.compilation.model.DiagnosticDescriptor;

/**
 * Statement that uses the JDK compiler to compile.
 *
 * @author Filip Hrisafov
 */
class Jdk11CompilingStatement extends JdkCompilingStatement {

    Jdk11CompilingStatement(FrameworkMethod method, CompilationCache compilationCache) {
        super( method, compilationCache );
    }


    /**
     * The JDK 11 compiler reports all ERROR diagnostics properly. Also when there are multiple per line.
     */
    @Override
    protected List<DiagnosticDescriptor> filterExpectedDiagnostics(List<DiagnosticDescriptor> expectedDiagnostics) {
        return expectedDiagnostics;
    }

    @Override
    protected String getPathSuffix() {
        return "_jdk";
    }
}
