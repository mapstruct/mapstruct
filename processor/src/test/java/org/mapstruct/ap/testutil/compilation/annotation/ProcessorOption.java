/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.testutil.compilation.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * An annotation processor option to be used for a test. Will be passed in the
 * form {@code -Aname=value} to the compiler.
 *
 * @author Gunnar Morling
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE, ElementType.METHOD })
@Repeatable( ProcessorOptions.class )
public @interface ProcessorOption {

    /**
     * The name of the processor option.
     *
     * @return The name of the processor option.
     */
    String name();

    /**
     * The value of the processor option.
     *
     * @return The value of the processor option.
     */
    String value();
}
