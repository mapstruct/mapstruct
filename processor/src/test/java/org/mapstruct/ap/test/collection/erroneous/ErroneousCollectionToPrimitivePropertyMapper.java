/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.collection.erroneous;

import org.mapstruct.Mapper;

@Mapper
public interface ErroneousCollectionToPrimitivePropertyMapper {

    Target mapCollectionToNonCollectionAsProperty(Source source);

}
