/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.testutil;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Specifies an implementation of an SPI to be used during the annotation processing.
 *
 * @author Andreas Gudian
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE, ElementType.METHOD })
@Repeatable( WithServiceImplementations.class )
public @interface WithServiceImplementation {
    /**
     * @return The service implementation class that is to be made available during the annotation processing.
     */
    Class<?> value();

    /**
     * @return The SPI that the service implementation provides. If omitted, then {@link #value()} is expected to
     *         implement exactly one interface which will be considered the provided SPI implementation.
     */
    Class<?> provides() default Object.class;
}
