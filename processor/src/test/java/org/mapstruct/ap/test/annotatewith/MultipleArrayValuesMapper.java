/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.annotatewith;

import org.mapstruct.AnnotateWith;
import org.mapstruct.Mapper;

/**
 * @author Ben Zegveld
 */
@Mapper
@AnnotateWith( value = CustomAnnotationWithParams.class, elements = {
    @AnnotateWith.Element( name = "stringArray", strings = { "test1", "test2" } ),
    @AnnotateWith.Element( name = "booleanArray", booleans = { false, true } ),
    @AnnotateWith.Element( name = "byteArray", bytes = { 0x08, 0x1f } ),
    @AnnotateWith.Element( name = "charArray", chars = { 'b', 'c' } ),
    @AnnotateWith.Element( name = "doubleArray", doubles = { 1.2, 3.4 } ),
    @AnnotateWith.Element( name = "floatArray", floats = { 1.2f, 3.4f } ),
    @AnnotateWith.Element( name = "intArray", ints = { 12, 34 } ),
    @AnnotateWith.Element( name = "longArray", longs = { 12L, 34L } ),
    @AnnotateWith.Element( name = "shortArray", shorts = { 12, 34 } ),
    @AnnotateWith.Element( name = "classArray", classes = { Mapper.class, CustomAnnotationWithParams.class } ),
    @AnnotateWith.Element( name = "stringParam", strings = "required parameter" )
} )
public interface MultipleArrayValuesMapper {

}
