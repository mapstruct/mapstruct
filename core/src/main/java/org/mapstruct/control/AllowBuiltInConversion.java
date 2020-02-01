/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.control;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.mapstruct.util.Experimental;

/**
 * Controls the mapping, allows for type conversion from source type to target type
 *
 * Type conversions are typically suppored directly in Java. The "toString()" is such an example,
 * which allows for mapping for instance a  {@link java.lang.Number} type to a {@link java.lang.String}.
 *
 * Please refer to the MapStruct guide for more info.
 *
 * @author Sjaak Derksen
 *
 * @since 1.4
 */
@Retention(RetentionPolicy.CLASS)
@Target( ElementType.ANNOTATION_TYPE )
@Experimental
public @interface AllowBuiltInConversion {
}
