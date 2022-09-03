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
@AnnotateWith( value = CustomAnnotationWithParams.class,
                elements = { @Element( name = "enumParam", enums = "EXISTING" ),
                    @Element( name = "stringParam", strings = "required" ) }
)
public interface ErroneousMapperWithMissingEnumClass {

}
