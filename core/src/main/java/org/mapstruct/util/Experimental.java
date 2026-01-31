/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.util;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Features that are marked with this annotation are considered <em>experimental</em>.
 *
 * @author Andreas Gudian
 */
@Documented
@Retention(RetentionPolicy.SOURCE)
public @interface Experimental {

    /**
     * The reason why the feature is considered experimental.
     *
     * @return the reason why the feature is considered experimental.
     */
    String value() default "";
}
