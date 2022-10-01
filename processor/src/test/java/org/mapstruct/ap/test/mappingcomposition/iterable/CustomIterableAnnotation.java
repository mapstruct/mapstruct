/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.mappingcomposition.iterable;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import org.mapstruct.IterableMapping;

/**
 * @author orange add
 */
@Retention( RetentionPolicy.CLASS )
@IterableMapping(numberFormat = "$#")
public @interface CustomIterableAnnotation {
}
