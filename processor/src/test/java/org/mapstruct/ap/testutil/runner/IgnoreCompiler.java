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
 * Annotation that can be used to ignore certain tests on certain compiler.
 * <p>
 * Use this as a last resort if there are bugs in the compilers that can't be fixed by us.
 *
 * @author Filip Hrisafov
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface IgnoreCompiler {
    /**
     * @return The compiler to ignore.
     */
    Compiler value();

    /**
     * @return The reason why the test is ignored. Usually a reference to a compiler bug
     */
    String reason();
}
