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
           parameters = {
               @AnnotateWith.Parameter( key = "stringParam", booleans = true ),
               @AnnotateWith.Parameter( key = "stringParam", bytes = 0x12 ),
               @AnnotateWith.Parameter( key = "stringParam", chars = 'a' ),
               @AnnotateWith.Parameter( key = "stringParam", classes = CustomAnnotationWithParams.class ),
               @AnnotateWith.Parameter( key = "stringParam", doubles = 12.34 ),
               @AnnotateWith.Parameter( key = "stringParam", floats = 12.34f ),
               @AnnotateWith.Parameter( key = "stringParam", ints = 1234 ),
               @AnnotateWith.Parameter( key = "stringParam", longs = 1234L ),
               @AnnotateWith.Parameter( key = "stringParam", shorts = 12 ),
               @AnnotateWith.Parameter( key = "stringParam", strings = "correctValue" ),
               @AnnotateWith.Parameter( key = "genericTypedClass", strings = "wrong"),
               @AnnotateWith.Parameter( key = "genericTypedClass", classes = ErroneousMapperWithWrongParameter.class )
           }
)
public class ErroneousMapperWithWrongParameter {

}
