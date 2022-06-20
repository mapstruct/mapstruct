/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.testutil.runner;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.extension.Extension;
import org.junit.jupiter.api.extension.TestTemplateInvocationContext;
import org.mapstruct.testutil.ProcessorTestConfiguration;

/**
 * The template invocation processor responsible for providing the appropriate extensions for the different compilers.
 *
 * @author Filip Hrisafov
 */
public class ProcessorTestInvocationContext implements TestTemplateInvocationContext {

    protected Compiler compiler;
    private final ProcessorTestConfiguration configuration;

    public ProcessorTestInvocationContext(Compiler compiler, ProcessorTestConfiguration configuration) {
        this.compiler = compiler;
        this.configuration = configuration;
    }

    @Override
    public String getDisplayName(int invocationIndex) {
        return "[" + compiler.name().toLowerCase() + "]";
    }

    @Override
    public List<Extension> getAdditionalExtensions() {
        List<Extension> extensions = new ArrayList<>();
        extensions.add( new CompilerTestEnabledOnJreCondition( compiler ) );
        if ( compiler == Compiler.JDK ) {
            extensions.add( new JdkCompilingExtension( configuration ) );
        }
        else if ( compiler == Compiler.ECLIPSE ) {
            extensions.add( new EclipseCompilingExtension( configuration ) );
        }
        else {
            throw new IllegalArgumentException( "Compiler [" + compiler + "] is not known" );
        }

        return extensions;
    }
}
