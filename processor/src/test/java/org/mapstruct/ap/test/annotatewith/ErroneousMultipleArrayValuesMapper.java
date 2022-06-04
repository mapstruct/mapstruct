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
    @Element( name = "stringParam", strings = { "test1", "test2" } ),
    @Element( name = "booleanParam", booleans = { false, true } ),
    @Element( name = "byteParam", bytes = { 0x08, 0x1f } ),
    @Element( name = "charParam", chars = { 'b', 'c' } ),
    @Element( name = "doubleParam", doubles = { 1.2, 3.4 } ),
    @Element( name = "floatParam", floats = { 1.2f, 3.4f } ),
    @Element( name = "intParam", ints = { 12, 34 } ),
    @Element( name = "longParam", longs = { 12L, 34L } ),
    @Element( name = "shortParam", shorts = { 12, 34 } ),
    @Element( name = "genericTypedClass", classes = { Mapper.class, CustomAnnotationWithParams.class } ),
    @Element( name = "enumParam", enumClass = AnnotateWithEnum.class, enums = { "EXISTING", "OTHER_EXISTING" } )
} )
public interface ErroneousMultipleArrayValuesMapper {

}
