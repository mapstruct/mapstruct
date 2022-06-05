/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.annotatewith;

import org.mapstruct.AnnotateWith;
import org.mapstruct.Mapper;

@Mapper
@AnnotateWith( value = CustomAnnotation.class, elements = @AnnotateWith.Element( strings = "value" ) )
public interface AnnotationWithoutElementNameMapper {

}
