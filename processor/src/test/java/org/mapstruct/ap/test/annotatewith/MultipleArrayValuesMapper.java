/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.annotatewith;

import org.mapstruct.AnnotateWith;
import org.mapstruct.AnnotateWith.Element;
import org.mapstruct.Mapper;

/**
 * @author Ben Zegveld
 */
@Mapper
@AnnotateWith( value = CustomAnnotationWithParams.class, elements = {
    @Element( name = "stringArray", strings = { "test1", "test2" } ),
    @Element( name = "booleanArray", booleans = { false, true } ),
    @Element( name = "byteArray", bytes = { 0x08, 0x1f } ),
    @Element( name = "charArray", chars = { 'b', 'c' } ),
    @Element( name = "doubleArray", doubles = { 1.2, 3.4 } ),
    @Element( name = "floatArray", floats = { 1.2f, 3.4f } ),
    @Element( name = "intArray", ints = { 12, 34 } ),
    @Element( name = "longArray", longs = { 12L, 34L } ),
    @Element( name = "shortArray", shorts = { 12, 34 } ),
    @Element( name = "classArray", classes = { Mapper.class, CustomAnnotationWithParams.class } ),
    @Element( name = "stringParam", strings = "required parameter" )
} )
public interface MultipleArrayValuesMapper {

}
