/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._3849;

import java.util.ArrayList;
import java.util.List;

import org.mapstruct.AfterMapping;
import org.mapstruct.BeforeMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper
public interface DeduplicateGenericMapper {

    DeduplicateGenericMapper INSTANCE = Mappers.getMapper( DeduplicateGenericMapper.class );
    List<String> INVOKED_METHODS = new ArrayList<>();

    ParentDto mapParent(Parent source);

    ChildDto mapChild(Parent source);

    @BeforeMapping
    default <T extends ParentDto> void deduplicateBefore(Parent source, @MappingTarget T target) {
        INVOKED_METHODS.add( "beforeMappingParentGeneric" );
    }

    @BeforeMapping
    default void deduplicateBefore(Parent source, @MappingTarget ChildDto target) {
        INVOKED_METHODS.add( "beforeMappingChild" );
    }

    @AfterMapping
    default <T> void deduplicate(Parent source, @MappingTarget T target) {
        INVOKED_METHODS.add( "afterMappingGeneric" );
    }

    @AfterMapping
    default void deduplicate(Parent source, @MappingTarget ParentDto target) {
        INVOKED_METHODS.add( "afterMappingParent" );
    }

    @AfterMapping
    default void deduplicate(Parent source, @MappingTarget ChildDto target) {
        INVOKED_METHODS.add( "afterMappingChild" );
    }
}
