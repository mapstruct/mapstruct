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
    @AnnotateWith.Parameter( key = "stringArray", strings = "test" ),
    @AnnotateWith.Parameter( key = "stringParam", strings = "test" ),
    @AnnotateWith.Parameter( key = "booleanArray", booleans = true ),
    @AnnotateWith.Parameter( key = "booleanParam", booleans = true ),
    @AnnotateWith.Parameter( key = "byteArray", bytes = 0x10 ),
    @AnnotateWith.Parameter( key = "byteParam", bytes = 0x13 ),
    @AnnotateWith.Parameter( key = "charArray", chars = 'd' ),
    @AnnotateWith.Parameter( key = "charParam", chars = 'a' ),
    @AnnotateWith.Parameter( key = "doubleArray", doubles = 0.3 ),
    @AnnotateWith.Parameter( key = "doubleParam", doubles = 1.2 ),
    @AnnotateWith.Parameter( key = "floatArray", floats = 0.3f ),
    @AnnotateWith.Parameter( key = "floatParam", floats = 1.2f ),
    @AnnotateWith.Parameter( key = "intArray", ints = 3 ),
    @AnnotateWith.Parameter( key = "intParam", ints = 1 ),
    @AnnotateWith.Parameter( key = "longArray", longs = 3L ),
    @AnnotateWith.Parameter( key = "longParam", longs = 1L ),
    @AnnotateWith.Parameter( key = "shortArray", shorts = 3 ),
    @AnnotateWith.Parameter( key = "shortParam", shorts = 1 )
} )
public interface CustomNamedMapper {

}
