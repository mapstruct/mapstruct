/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.testutil.compilation.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Specifies the expected outcome of a compilation, comprising the actual result
 * and optionally one or more diagnostics.
 *
 * @author Gunnar Morling
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface ExpectedCompilationOutcome {

    /**
     * The expected result of a compilation.
     *
     * @return The expected result of a compilation.
     */
    CompilationResult value();

    /**
     * The expected diagnostics created during a compilation.
     *
     * @return The expected diagnostics created during a compilation.
     */
    Diagnostic[] diagnostics() default { };
}
