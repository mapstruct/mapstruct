/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.collection.erroneous;

import java.util.Set;

import org.mapstruct.Mapper;

@Mapper
public interface ErroneousCollectionToNonCollectionMapper {

    Integer stringSetToInteger(Set<String> strings);

    Set<String> integerToStringSet(Integer integer);
}
