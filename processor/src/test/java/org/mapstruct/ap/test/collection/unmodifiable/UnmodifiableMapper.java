/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.collection.unmodifiable;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;
import java.util.NavigableSet;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;

import org.mapstruct.Mapper;
import org.mapstruct.Unmodifiable;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UnmodifiableMapper {
    UnmodifiableMapper INSTANCE = Mappers.getMapper( UnmodifiableMapper.class );

    @Unmodifiable
    Collection<Target> mapToCollection(List<Source> sources);

    @Unmodifiable
    List<Target> mapToList(List<Source> sources);

    @Unmodifiable
    Set<Target> mapToSet(List<Source> sources);

    @Unmodifiable
    NavigableSet<Target> mapToNavigableSet(List<Source> sources);

    @Unmodifiable
    SortedSet<Target> mapToSortedSet(List<Source> sources);

    @Unmodifiable
    Map<String, Target> mapToMap(Map<String, Source> sources);

    @Unmodifiable
    NavigableMap<String, Target> mapToNavigableMap(Map<String, Source> sources);

    @Unmodifiable
    SortedMap<String, Target> mapToSortedMap(Map<String, Source> sources);
}
