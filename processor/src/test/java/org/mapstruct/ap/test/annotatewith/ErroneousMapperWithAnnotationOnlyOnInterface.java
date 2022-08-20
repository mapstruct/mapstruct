/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.annotatewith;

import org.mapstruct.AnnotateWith;
import org.mapstruct.Mapper;

/**
 * @author Filip Hrisafov
 */
@Mapper
@AnnotateWith( value = CustomAnnotationOnlyAnnotation.class )
public interface ErroneousMapperWithAnnotationOnlyOnInterface {

}
