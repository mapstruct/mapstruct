/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.testutil.runner;

import java.lang.reflect.Method;
import java.util.stream.Stream;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestTemplateInvocationContext;
import org.junit.jupiter.api.extension.TestTemplateInvocationContextProvider;
import org.junit.platform.commons.support.AnnotationSupport;
import org.mapstruct.ap.testutil.ProcessorTest;

/**
 * The provider of the processor tests based on the defined compilers.
 *
 * @author Filip Hrisafov
 */
public class ProcessorTestExtension implements TestTemplateInvocationContextProvider {

    @Override
    public boolean supportsTestTemplate(ExtensionContext context) {
        return AnnotationSupport.isAnnotated( context.getTestMethod(), ProcessorTest.class );
    }

    @Override
    public Stream<TestTemplateInvocationContext> provideTestTemplateInvocationContexts(ExtensionContext context) {
        Method testMethod = context.getRequiredTestMethod();
        ProcessorTest processorTest = AnnotationSupport.findAnnotation( testMethod, ProcessorTest.class )
            .orElseThrow( () -> new RuntimeException( "Failed to get CompilerTest on " + testMethod ) );

        return Stream.of( processorTest.value() )
            .map( ProcessorTestInvocationContext::new );
    }
}
