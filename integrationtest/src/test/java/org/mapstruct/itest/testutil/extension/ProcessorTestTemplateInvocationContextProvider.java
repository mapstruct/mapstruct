/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.itest.testutil.extension;

import java.lang.reflect.Method;
import java.util.stream.Stream;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestTemplateInvocationContext;
import org.junit.jupiter.api.extension.TestTemplateInvocationContextProvider;
import org.junit.platform.commons.support.AnnotationSupport;

/**
 * @author Filip Hrisafov
 */
public class ProcessorTestTemplateInvocationContextProvider implements TestTemplateInvocationContextProvider {
    @Override
    public boolean supportsTestTemplate(ExtensionContext context) {
        return AnnotationSupport.isAnnotated( context.getTestMethod(), ProcessorTest.class );
    }

    @Override
    public Stream<TestTemplateInvocationContext> provideTestTemplateInvocationContexts(ExtensionContext context) {

        Method testMethod = context.getRequiredTestMethod();
        ProcessorTest processorTest = AnnotationSupport.findAnnotation( testMethod, ProcessorTest.class )
            .orElseThrow( () -> new RuntimeException( "Failed to get ProcessorTest on " + testMethod ) );


        return Stream.of( processorTest.processorTypes() )
            .map( processorType -> new ProcessorTestTemplateInvocationContext( new ProcessorTestContext(
                processorTest.baseDir(),
                processorType,
                processorTest.commandLineEnhancer()
            ) ) );
    }
}
