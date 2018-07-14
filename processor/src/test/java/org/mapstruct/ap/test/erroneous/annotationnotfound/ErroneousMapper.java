/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.erroneous.annotationnotfound;

import org.mapstruct.Mapper;

/**
 *
 * @author Sjaak Derksen
 */
@Mapper
public interface ErroneousMapper {

    @NotFoundAnnotation
    Target map( Source source );
}
