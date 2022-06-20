/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.testutil;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.testutil.runner.Compiler;
import org.mapstruct.testutil.runner.ProcessorTestExtension;

/**
 * JUnit Jupiter test template for the MapStruct Processor tests.
 * <p>
 * Test classes are safe to be executed in parallel, but test methods are not safe to be executed in parallel.
 * <p>
 * By default this template would generate tests for the JDK and Eclipse Compiler.
 * If only a single compiler is needed then specify the compiler in the value.
 * <p>
 * The classes to be compiled for a given test method must be specified via {@link WithClasses}. In addition the
 * following things can be configured optionally :
 * <ul>
 * <li>Processor options to be considered during compilation via
 * {@link org.mapstruct.testutil.compilation.annotation.ProcessorOption ProcessorOption}.</li>
 * <li>The expected compilation outcome and expected diagnostics can be specified via
 * {@link org.mapstruct.testutil.compilation.annotation.ExpectedCompilationOutcome ExpectedCompilationOutcome}.
 * If no outcome is specified, a successful compilation is assumed.</li>
 * </ul>
 *
 * @author Filip Hrisafov
 */
@Target({ ElementType.ANNOTATION_TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@TestTemplate
@ExtendWith(ProcessorTestExtension.class)
public @interface ProcessorTest {

    Compiler[] value() default {
        Compiler.JDK,
        Compiler.ECLIPSE
    };
}
