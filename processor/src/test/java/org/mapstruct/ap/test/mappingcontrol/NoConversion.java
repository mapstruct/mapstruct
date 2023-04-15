/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.mappingcontrol;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import org.mapstruct.control.MappingControl;
import org.mapstruct.util.Experimental;

@Retention(RetentionPolicy.CLASS)
@Experimental
@MappingControl( MappingControl.Use.DIRECT )
@MappingControl( MappingControl.Use.MAPPING_METHOD )
@MappingControl( MappingControl.Use.COMPLEX_MAPPING )
public @interface NoConversion {
}
