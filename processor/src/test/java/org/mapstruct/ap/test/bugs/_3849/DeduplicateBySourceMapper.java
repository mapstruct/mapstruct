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
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper
public interface DeduplicateBySourceMapper {

    DeduplicateBySourceMapper INSTANCE = Mappers.getMapper( DeduplicateBySourceMapper.class );
    List<String> INVOKED_METHODS = new ArrayList<>();

    ParentDto mapParent(Parent source, @Context MappingContext context);

    ParentDto mapChild(Child source, @Context MappingContext context);

    class MappingContext {
        @BeforeMapping
        void deduplicateBySourceForBefore(Parent source, @MappingTarget ParentDto target) {
            INVOKED_METHODS.add( "beforeMappingParentSourceInOtherClass" );
        }

        @BeforeMapping
        void deduplicateBySourceForBefore(Child sourceChild, @MappingTarget ParentDto target) {
            INVOKED_METHODS.add( "beforeMappingChildSourceInOtherClass" );
        }

        @AfterMapping
        void deduplicateBySource(Parent source, @MappingTarget ParentDto target) {
            INVOKED_METHODS.add( "afterMappingParentSourceInOtherClass" );
        }

        @AfterMapping
        void deduplicateBySource(Child sourceChild, @MappingTarget ParentDto target) {
            INVOKED_METHODS.add( "afterMappingChildSourceInOtherClass" );
        }
    }

    @BeforeMapping
    default void deduplicateBySourceForBefore(Parent source, @MappingTarget ParentDto target) {
        INVOKED_METHODS.add( "beforeMappingParentSource" );
    }

    @BeforeMapping
    default void deduplicateBySourceForBefore(Child sourceChild, @MappingTarget ParentDto target) {
        INVOKED_METHODS.add( "beforeMappingChildSource" );
    }

    @AfterMapping
    default void deduplicateBySource(Parent source, @MappingTarget ParentDto target) {
        INVOKED_METHODS.add( "afterMappingParentSource" );
    }

    @AfterMapping
    default void deduplicateBySource(Child sourceChild, @MappingTarget ParentDto target) {
        INVOKED_METHODS.add( "afterMappingChildSource" );
    }
}
