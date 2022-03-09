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
    @AnnotateWith.Parameter( key = "stringParam", strings = { "test1", "test2" } ),
    @AnnotateWith.Parameter( key = "booleanParam", booleans = { false, true } ),
    @AnnotateWith.Parameter( key = "byteParam", bytes = { 0x08, 0x1f } ),
    @AnnotateWith.Parameter( key = "charParam", chars = { 'b', 'c' } ),
    @AnnotateWith.Parameter( key = "doubleParam", doubles = { 1.2, 3.4 } ),
    @AnnotateWith.Parameter( key = "floatParam", floats = { 1.2f, 3.4f } ),
    @AnnotateWith.Parameter( key = "intParam", ints = { 12, 34 } ),
    @AnnotateWith.Parameter( key = "longParam", longs = { 12L, 34L } ),
    @AnnotateWith.Parameter( key = "shortParam", shorts = { 12, 34 } ),
    @AnnotateWith.Parameter( key = "genericTypedClass", classes = { Mapper.class, CustomAnnotationWithParams.class } )
} )
public class ErroneousMultipleArrayValuesMapper {

}
