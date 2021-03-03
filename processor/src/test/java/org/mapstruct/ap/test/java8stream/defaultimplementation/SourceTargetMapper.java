/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.java8stream.defaultimplementation;

import java.util.Collection;
import java.util.List;
import java.util.NavigableSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.stream.Stream;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper
public interface SourceTargetMapper {

    SourceTargetMapper INSTANCE = Mappers.getMapper( SourceTargetMapper.class );

    @Mapping(target = "fooListNoSetter", source = "fooStream")
    Target sourceToTarget(Source source);

    TargetFoo sourceFooToTargetFoo(SourceFoo sourceFoo);

    List<TargetFoo> streamToList(Stream<SourceFoo> foos);

    Set<TargetFoo> streamToSet(Stream<SourceFoo> foos);

    Collection<TargetFoo> streamToCollection(Stream<SourceFoo> foos);

    Iterable<TargetFoo> streamToIterable(Stream<SourceFoo> foos);

    void sourceFoosToTargetFoosUsingTargetParameter(@MappingTarget List<TargetFoo> targetFoos,
                                                    Stream<SourceFoo> sourceFoos);

    Iterable<TargetFoo> sourceFoosToTargetFoosUsingTargetParameterAndReturn(Stream<SourceFoo> sourceFoos,
                                                                            @MappingTarget List<TargetFoo> targetFoos);

    SortedSet<TargetFoo> streamToSortedSet(Stream<SourceFoo> foos);

    NavigableSet<TargetFoo> streamToNavigableSet(Stream<SourceFoo> foos);

    void streamToArrayUsingTargetParameter(@MappingTarget TargetFoo[] targetFoos, Stream<SourceFoo> sourceFoo);

    TargetFoo[] streamToArrayUsingTargetParameterAndReturn(Stream<SourceFoo> sourceFoos,
                                                           @MappingTarget TargetFoo[] targetFoos);
}
