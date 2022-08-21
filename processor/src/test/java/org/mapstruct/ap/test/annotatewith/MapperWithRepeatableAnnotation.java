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
@AnnotateWith( value = CustomRepeatableAnnotation.class )
@AnnotateWith( value = CustomRepeatableAnnotation.class, elements = @AnnotateWith.Element( strings = "different" ) )
public interface MapperWithRepeatableAnnotation {

}
