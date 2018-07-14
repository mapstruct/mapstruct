/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.testutil.runner;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Temporarily only use the specified compiler for the test during debugging / implementation.
 * <p>
 * Do not commit tests with this annotation present!
 *
 * @deprecated Do not commit tests with this annotation present. Tests are expected to work with all compilers.
 * @author Andreas Gudian
 */
@Deprecated
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface WithSingleCompiler {
    /**
     * @return The compiler to use.
     */
    Compiler value();
}
