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
    @AnnotateWith.Parameter( key = "stringParam", strings = "test" ),
    @AnnotateWith.Parameter( key = "genericTypedClass", classes = Mapper.class )
} )
public interface CustomNamedGenericClassMapper {

}
