/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.generics.wildcard;

import org.mapstruct.Mapper;

@Mapper
public interface ErroneousCollectionSuperToExtendMapper {

    CollectionExtendTypes map(CollectionSuperTypes types);
}
