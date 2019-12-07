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
 * Controls the mapping, allows for a direct mapping from source type to target type.
 *
 * This means if source type and target type are of the same type, MapStruct will not perform
 * any mappings anymore and assign the target to the source direct.
 *
 * An exception are types from the package {@link java.lang}, which will be mapped always directly.
 *
 * @author Sjaak Derksen
 *
 * @since 1.4
 */
@Retention(RetentionPolicy.CLASS)
@Target( ElementType.ANNOTATION_TYPE )
@Experimental
public @interface AllowDirect {
}
