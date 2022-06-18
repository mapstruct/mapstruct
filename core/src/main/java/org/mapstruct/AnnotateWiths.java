/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.CLASS;

/**
 * This can be used to have mapstruct generate additional annotations on classes/methods.
 *
 * @author Ben Zegveld
 * @since 1.6
 */
@Retention( CLASS )
@Target( { TYPE, METHOD } )
public @interface AnnotateWiths {

    /**
     * The configuration of the additional annotations.
     *
     * @return The configuration of the additional annotations.
     */
    AnnotateWith[] value();
}
