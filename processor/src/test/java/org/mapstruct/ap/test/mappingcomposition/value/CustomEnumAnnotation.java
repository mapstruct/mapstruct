/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.mappingcomposition.value;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import org.mapstruct.EnumMapping;

/**
 * @author orange add
 */
@EnumMapping(nameTransformationStrategy = "prefix", configuration = "PREFIX_")
@Retention( RetentionPolicy.CLASS )
public @interface CustomEnumAnnotation {
}
