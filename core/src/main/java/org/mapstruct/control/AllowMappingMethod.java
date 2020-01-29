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
 * Controls the mapping, allows for Direct Mapping from source type to target type.
 *
 * The mapping method can be either a custom refered mapping method, or a MapStruct built in
 * mapping method.
 *
 * @author Sjaak Derksen
 *
 * @since 1.4
 */
@Retention(RetentionPolicy.CLASS)
@Target( ElementType.ANNOTATION_TYPE )
@Experimental
public @interface AllowMappingMethod {
}
