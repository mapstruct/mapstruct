/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.mapstruct.util.Experimental;

/**
 * Configuration of builders, e.g. the name of the final build method.
 *
 * @author Filip Hrisafov
 *
 * @since 1.3
 */
@Retention(RetentionPolicy.CLASS)
@Target({})
@Experimental
public @interface Builder {

    /**
     * The name of the build method that needs to be invoked on the builder to create the type to be build
     *
     * @return the method that needs to tbe invoked on the builder
     */
    String buildMethod() default "build";
}
