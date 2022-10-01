/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.mappingcomposition.map;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import org.mapstruct.MapMapping;

/**
 * @author orange add
 */
@Retention( RetentionPolicy.CLASS )
@MapMapping(valueNumberFormat = "$#")
public @interface CustomMapAnnotation {
}
