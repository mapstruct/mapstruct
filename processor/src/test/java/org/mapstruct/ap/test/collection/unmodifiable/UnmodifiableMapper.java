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

import org.mapstruct.IterableMapping;
import org.mapstruct.MapMapping;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UnmodifiableMapper {
    UnmodifiableMapper INSTANCE = Mappers.getMapper( UnmodifiableMapper.class );

    @IterableMapping(unmodifiable = true)
    Collection<Target> mapToCollection(List<Source> sources);

    @IterableMapping(unmodifiable = true)
    List<Target> mapToList(List<Source> sources);

    @IterableMapping(unmodifiable = true)
    Set<Target> mapToSet(List<Source> sources);

    @IterableMapping(unmodifiable = true)
    NavigableSet<Target> mapToNavigableSet(List<Source> sources);

    @IterableMapping(unmodifiable = true)
    SortedSet<Target> mapToSortedSet(List<Source> sources);

    @MapMapping(unmodifiable = true)
    Map<String, Target> mapToMap(Map<String, Source> sources);

    @MapMapping(unmodifiable = true)
    NavigableMap<String, Target> mapToNavigableMap(Map<String, Source> sources);

    @MapMapping(unmodifiable = true)
    SortedMap<String, Target> mapToSortedMap(Map<String, Source> sources);
}
