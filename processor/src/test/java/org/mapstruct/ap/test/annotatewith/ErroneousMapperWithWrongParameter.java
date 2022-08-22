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
@AnnotateWith( value = CustomAnnotationWithParams.class,
               elements = @AnnotateWith.Element( name = "stringParam", booleans = true ) )
@AnnotateWith( value = CustomAnnotationWithParams.class,
               elements = @AnnotateWith.Element( name = "stringParam", bytes = 0x12 ) )
@AnnotateWith( value = CustomAnnotationWithParams.class,
               elements = @AnnotateWith.Element( name = "stringParam", chars = 'a' ) )
@AnnotateWith( value = CustomAnnotationWithParams.class,
               elements = @AnnotateWith.Element( name = "stringParam", classes = CustomAnnotationWithParams.class ) )
@AnnotateWith( value = CustomAnnotationWithParams.class,
               elements = @AnnotateWith.Element( name = "stringParam", doubles = 12.34 ) )
@AnnotateWith( value = CustomAnnotationWithParams.class,
               elements = @AnnotateWith.Element( name = "stringParam", floats = 12.34f ) )
@AnnotateWith( value = CustomAnnotationWithParams.class,
               elements = @AnnotateWith.Element( name = "stringParam", ints = 1234 ) )
@AnnotateWith( value = CustomAnnotationWithParams.class,
               elements = @AnnotateWith.Element( name = "stringParam", longs = 1234L ) )
@AnnotateWith( value = CustomAnnotationWithParams.class,
               elements = @AnnotateWith.Element( name = "stringParam", shorts = 12 ) )
@AnnotateWith( value = CustomAnnotationWithParams.class, elements = {
    @AnnotateWith.Element( name = "stringParam", strings = "correctValue" ),
    @AnnotateWith.Element( name = "genericTypedClass", strings = "wrong" ),
    @AnnotateWith.Element( name = "enumParam", enumClass = WrongAnnotateWithEnum.class, enums = "EXISTING" )
} )
@AnnotateWith( value = CustomAnnotationWithParams.class, elements = {
    @AnnotateWith.Element( name = "stringParam", strings = "correctValue" ),
    @AnnotateWith.Element( name = "genericTypedClass", classes = ErroneousMapperWithWrongParameter.class )
} )
@AnnotateWith( value = CustomAnnotation.class, elements = @AnnotateWith.Element( booleans = true ) )
public interface ErroneousMapperWithWrongParameter {

}
