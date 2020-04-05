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
 * This should be used with care.
 * This is similar to the JUnit 5 DisabledOnJre (once we have JUnit 5 we can replace this one)
 *
 * @author Filip Hrisafov
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DisabledOnCompiler {
    /**
     * @return The compiler to use.
     */
    Compiler[] value();
}
