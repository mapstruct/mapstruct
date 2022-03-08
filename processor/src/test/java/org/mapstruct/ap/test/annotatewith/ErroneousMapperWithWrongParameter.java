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
                   @AnnotateWith.Parameter( key = "stringParam",
                                                          booleans = true,
                                                          bytes = 0x12,
                                                          chars = 'a',
                                                          classes = CustomAnnotationWithParams.class,
                                                          doubles = 12.34,
                                                          floats = 12.34f,
                                                          ints = 1234,
                                                          longs = 1234L,
                                                          shorts = 12,
                                                          strings = "correctValue" ),
                   @AnnotateWith.Parameter( key = "genericTypedClass",
                                                          strings = "wrong",
                                                          classes = ErroneousMapperWithWrongParameter.class )
               }
)
public class ErroneousMapperWithWrongParameter {

}
