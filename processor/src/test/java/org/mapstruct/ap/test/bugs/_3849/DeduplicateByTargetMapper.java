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
public interface DeduplicateByTargetMapper {

    DeduplicateByTargetMapper INSTANCE = Mappers.getMapper( DeduplicateByTargetMapper.class );
    List<String> INVOKED_METHODS = new ArrayList<>();

    ParentDto mapParent(Parent source, @Context MappingContext context);

    ChildDto mapChild(Parent source, @Context MappingContext context);

    class MappingContext {
        @BeforeMapping
        void deduplicateByTargetForBefore(Parent source, @MappingTarget ParentDto target) {
            INVOKED_METHODS.add( "beforeMappingParentTargetInOtherClass" );
        }

        @BeforeMapping
        void deduplicateByTargetForBefore(Parent source, @MappingTarget ChildDto target) {
            INVOKED_METHODS.add( "beforeMappingChildTargetInOtherClass" );
        }

        @AfterMapping
        void deduplicateByTarget(Parent source, @MappingTarget ParentDto target) {
            INVOKED_METHODS.add( "afterMappingParentTargetInOtherClass" );
        }

        @AfterMapping
        void deduplicateByTarget(Parent source, @MappingTarget ChildDto target) {
            INVOKED_METHODS.add( "afterMappingChildTargetInOtherClass" );
        }
    }

    @BeforeMapping
    default void deduplicateByTargetForBefore(Parent source, @MappingTarget ParentDto target) {
        INVOKED_METHODS.add( "beforeMappingParentTarget" );
    }

    @BeforeMapping
    default void deduplicateByTargetForBefore(Parent source, @MappingTarget ChildDto target) {
        INVOKED_METHODS.add( "beforeMappingChildTarget" );
    }

    @AfterMapping
    default void deduplicateByTarget(Parent source, @MappingTarget ParentDto target) {
        INVOKED_METHODS.add( "afterMappingParentTarget" );
    }

    @AfterMapping
    default void deduplicateByTarget(Parent source, @MappingTarget ChildDto target) {
        INVOKED_METHODS.add( "afterMappingChildTarget" );
    }
}
