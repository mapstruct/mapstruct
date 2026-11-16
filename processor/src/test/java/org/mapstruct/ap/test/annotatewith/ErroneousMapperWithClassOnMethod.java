/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.annotatewith;

import org.mapstruct.AnnotateWith;
import org.mapstruct.Mapper;
import org.mapstruct.ap.test.WithProperties;

/**
 * @author Ben Zegveld
 */
@Mapper
public interface ErroneousMapperWithClassOnMethod {

    @AnnotateWith( value = CustomClassOnlyAnnotation.class )
    WithProperties toString(String string);

}
