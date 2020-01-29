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
 * Controls the mapping from source to target type, allows mapping by calling:
 * <ol>
 * <li>a type conversion, passed into a mapping method</li>
 * <li>a mapping method, passed into a type conversion</li>
 * <li>a mapping method passed into another mapping method</li>
 * </ol>

 * @author Sjaak Derksen
 *
 * @since 1.4
 */
@Retention(RetentionPolicy.CLASS)
@Target( ElementType.ANNOTATION_TYPE )
@Experimental
public @interface AllowComplexMapping {
}
