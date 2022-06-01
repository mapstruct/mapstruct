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
    @AnnotateWith.Element( name = "stringArray", strings = "test" ),
    @AnnotateWith.Element( name = "stringParam", strings = "test" ),
    @AnnotateWith.Element( name = "booleanArray", booleans = true ),
    @AnnotateWith.Element( name = "booleanParam", booleans = true ),
    @AnnotateWith.Element( name = "byteArray", bytes = 0x10 ),
    @AnnotateWith.Element( name = "byteParam", bytes = 0x13 ),
    @AnnotateWith.Element( name = "charArray", chars = 'd' ),
    @AnnotateWith.Element( name = "charParam", chars = 'a' ),
    @AnnotateWith.Element( name = "doubleArray", doubles = 0.3 ),
    @AnnotateWith.Element( name = "doubleParam", doubles = 1.2 ),
    @AnnotateWith.Element( name = "floatArray", floats = 0.3f ),
    @AnnotateWith.Element( name = "floatParam", floats = 1.2f ),
    @AnnotateWith.Element( name = "intArray", ints = 3 ),
    @AnnotateWith.Element( name = "intParam", ints = 1 ),
    @AnnotateWith.Element( name = "longArray", longs = 3L ),
    @AnnotateWith.Element( name = "longParam", longs = 1L ),
    @AnnotateWith.Element( name = "shortArray", shorts = 3 ),
    @AnnotateWith.Element( name = "shortParam", shorts = 1 )
} )
public interface CustomNamedMapper {

}
