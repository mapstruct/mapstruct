/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.itest.testutil.extension;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.condition.JRE;
import org.junit.jupiter.api.extension.Extension;
import org.junit.jupiter.api.extension.TestTemplateInvocationContext;

/**
 * @author Filip Hrisafov
 */
public class ProcessorTestTemplateInvocationContext implements TestTemplateInvocationContext {

    static final JRE CURRENT_VERSION;

    static {
        JRE currentVersion = JRE.OTHER;
        for ( JRE jre : JRE.values() ) {
            if ( jre.isCurrentVersion() ) {
                currentVersion = jre;
                break;
            }
        }

        CURRENT_VERSION = currentVersion;
    }

    private final ProcessorTestContext processorTestContext;

    public ProcessorTestTemplateInvocationContext(ProcessorTestContext processorTestContext) {
        this.processorTestContext = processorTestContext;
    }

    @Override
    public String getDisplayName(int invocationIndex) {
        return processorTestContext.getProcessor().name().toLowerCase();
    }

    @Override
    public List<Extension> getAdditionalExtensions() {
        List<Extension> extensions = new ArrayList<>();
        extensions.add( new ProcessorEnabledOnJreCondition( processorTestContext.getProcessor() ) );
        extensions.add( new ProcessorInvocationInterceptor( processorTestContext ) );
        return extensions;
    }
}
