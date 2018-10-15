/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Configures the mappings of several bean attributes.
 *
 * @author Gunnar Morling
 */
@Retention(RetentionPolicy.CLASS)
@Target(ElementType.METHOD)
public @interface Mappings {

    /**
     * The configuration of the bean attributes.
     *
     * @return The configuration of the bean attributes.
     */
    Mapping[] value();
}
