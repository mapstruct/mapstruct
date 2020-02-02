/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.control;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import org.mapstruct.util.Experimental;

/**
 * Clones a source type to a target type (assuming source and target are of the same type).
 *
 * @author Sjaak Derksen
 *
 * @since 1.4
 */
@Retention(RetentionPolicy.CLASS)
@Experimental
@MappingControl( MappingControl.Use.MAPPING_METHOD )
public @interface DeepClone {
}
