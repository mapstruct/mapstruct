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
@AnnotateWith( value = CustomAnnotationWithParams.class, parameters = {
    @AnnotateWith.Parameter( key = "stringArray", strings = { "test1", "test2" } ),
    @AnnotateWith.Parameter( key = "booleanArray", booleans = { false, true } ),
    @AnnotateWith.Parameter( key = "byteArray", bytes = { 0x08, 0x1f } ),
    @AnnotateWith.Parameter( key = "charArray", chars = { 'b', 'c' } ),
    @AnnotateWith.Parameter( key = "doubleArray", doubles = { 1.2, 3.4 } ),
    @AnnotateWith.Parameter( key = "floatArray", floats = { 1.2f, 3.4f } ),
    @AnnotateWith.Parameter( key = "intArray", ints = { 12, 34 } ),
    @AnnotateWith.Parameter( key = "longArray", longs = { 12L, 34L } ),
    @AnnotateWith.Parameter( key = "shortArray", shorts = { 12, 34 } ),
    @AnnotateWith.Parameter( key = "classArray", classes = { Mapper.class, CustomAnnotationWithParams.class } ),
    @AnnotateWith.Parameter( key = "stringParam", strings = "required parameter" )
} )
public class MultipleArrayValuesMapper {

}
