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
@AnnotateWith( value = CustomNamed.class,
               parameters = @AnnotateWith.Parameter( key = "value", booleans = true )
)
@AnnotateWith( value = CustomNamed.class,
               parameters = @AnnotateWith.Parameter( key = "value", bytes = 0x12 )
)
@AnnotateWith( value = CustomNamed.class,
               parameters = @AnnotateWith.Parameter( key = "value", chars = 'a' )
)
@AnnotateWith( value = CustomNamed.class,
               parameters = @AnnotateWith.Parameter( key = "value", classes = CustomNamed.class )
)
@AnnotateWith( value = CustomNamed.class,
               parameters = @AnnotateWith.Parameter( key = "value", doubles = 12.34 )
)
@AnnotateWith( value = CustomNamed.class,
               parameters = @AnnotateWith.Parameter( key = "value", floats = 12.34f )
)
@AnnotateWith( value = CustomNamed.class,
               parameters = @AnnotateWith.Parameter( key = "value", ints = 1234 )
)
@AnnotateWith( value = CustomNamed.class,
               parameters = @AnnotateWith.Parameter( key = "value", longs = 1234L )
)
@AnnotateWith( value = CustomNamed.class,
               parameters = @AnnotateWith.Parameter( key = "value", shorts = 12 )
)
@AnnotateWith( value = CustomNamed.class,
               parameters = {
                   @AnnotateWith.Parameter( key = "value", strings = "correct" ),
                   @AnnotateWith.Parameter( key = "genericTypedClass", strings = "wrong" ),
               }
)
@AnnotateWith( value = CustomNamed.class,
               parameters = {
                   @AnnotateWith.Parameter( key = "value", strings = "correct" ),
                   @AnnotateWith.Parameter( key = "genericTypedClass",
                                            classes = ErroneousMapperWithWrongParameter.class )
               }
)
public class ErroneousMapperWithWrongParameter {

}
