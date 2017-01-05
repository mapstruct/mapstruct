/**
 *  Copyright 2012-2017 Gunnar Morling (http://www.gunnarmorling.de/)
 *  and/or other contributors as indicated by the @authors tag. See the
 *  copyright.txt file in the distribution for a full listing of all
 *  contributors.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
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

    @Mapping(source = "fooStream", target = "fooListNoSetter")
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
