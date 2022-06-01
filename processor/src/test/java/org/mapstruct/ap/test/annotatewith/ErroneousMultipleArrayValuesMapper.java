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
    @AnnotateWith.Element( name = "stringParam", strings = { "test1", "test2" } ),
    @AnnotateWith.Element( name = "booleanParam", booleans = { false, true } ),
    @AnnotateWith.Element( name = "byteParam", bytes = { 0x08, 0x1f } ),
    @AnnotateWith.Element( name = "charParam", chars = { 'b', 'c' } ),
    @AnnotateWith.Element( name = "doubleParam", doubles = { 1.2, 3.4 } ),
    @AnnotateWith.Element( name = "floatParam", floats = { 1.2f, 3.4f } ),
    @AnnotateWith.Element( name = "intParam", ints = { 12, 34 } ),
    @AnnotateWith.Element( name = "longParam", longs = { 12L, 34L } ),
    @AnnotateWith.Element( name = "shortParam", shorts = { 12, 34 } ),
    @AnnotateWith.Element( name = "genericTypedClass", classes = { Mapper.class,
        CustomAnnotationWithParams.class } )
} )
public interface ErroneousMultipleArrayValuesMapper {

}
