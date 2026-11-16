/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.collection.defaultimplementation.jdk21;

import java.util.Collection;
import java.util.Map;
import java.util.SequencedMap;
import java.util.SequencedSet;

import org.mapstruct.Mapper;
import org.mapstruct.ap.test.collection.defaultimplementation.SourceFoo;
import org.mapstruct.ap.test.collection.defaultimplementation.TargetFoo;
import org.mapstruct.factory.Mappers;

@Mapper
public interface SequencedCollectionsMapper {

    SequencedCollectionsMapper INSTANCE = Mappers.getMapper( SequencedCollectionsMapper.class );

    SequencedSet<TargetFoo> sourceFoosToTargetFooSequencedSet(Collection<SourceFoo> foos);

    SequencedMap<String, TargetFoo> sourceFooMapToTargetFooSequencedMap(Map<Long, SourceFoo> foos);
}
