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
    @Element( name = "stringArray", strings = "test" ),
    @Element( name = "stringParam", strings = "test" ),
    @Element( name = "booleanArray", booleans = true ),
    @Element( name = "booleanParam", booleans = true ),
    @Element( name = "byteArray", bytes = 0x10 ),
    @Element( name = "byteParam", bytes = 0x13 ),
    @Element( name = "charArray", chars = 'd' ),
    @Element( name = "charParam", chars = 'a' ),
    @Element( name = "enumArray", 
              enums = @AnnotateWith.EnumElement( enumClass = AnnotateWithEnum.class, name = "EXISTING" ) ),
    @Element( name = "enumParam", 
              enums = @AnnotateWith.EnumElement( enumClass = AnnotateWithEnum.class, name = "EXISTING" ) ),
    @Element( name = "doubleArray", doubles = 0.3 ),
    @Element( name = "doubleParam", doubles = 1.2 ),
    @Element( name = "floatArray", floats = 0.3f ),
    @Element( name = "floatParam", floats = 1.2f ),
    @Element( name = "intArray", ints = 3 ),
    @Element( name = "intParam", ints = 1 ),
    @Element( name = "longArray", longs = 3L ),
    @Element( name = "longParam", longs = 1L ),
    @Element( name = "shortArray", shorts = 3 ),
    @Element( name = "shortParam", shorts = 1 )
} )
public interface CustomNamedMapper {

}
