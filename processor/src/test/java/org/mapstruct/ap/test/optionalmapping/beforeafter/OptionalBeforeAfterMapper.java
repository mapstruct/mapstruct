/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.optionalmapping.beforeafter;

import java.util.Optional;

import org.mapstruct.AfterMapping;
import org.mapstruct.BeforeMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.TargetType;
import org.mapstruct.factory.Mappers;

@Mapper
public interface OptionalBeforeAfterMapper {

    OptionalBeforeAfterMapper INSTANCE = Mappers.getMapper( OptionalBeforeAfterMapper.class );

    Target toTarget(Source source);

    @BeforeMapping
    default void beforeDeepOptionalSourceWithNoTargetType(Optional<Source.SubType> source) {
    }

    @BeforeMapping
    default void beforeDeepOptionalSourceWithNonOptionalTargetType(@TargetType Class<Target.SubType> targetType,
                                                                   Optional<Source.SubType> source) {
    }

    @AfterMapping
    default void afterDeepOptionalSourceWithNoTarget(Optional<Source.SubType> source) {

    }

    @AfterMapping
    default void afterDeepOptionalSourceWithNonOptionalTarget(@MappingTarget Target.SubType target,
                                                              Optional<Source.SubType> source) {
    }

    @AfterMapping
    default void afterDeepOptionalSourceWithOptionalTarget(@MappingTarget Optional<Target.SubType> target,
                                                           Optional<Source.SubType> source) {
    }

    @AfterMapping
    default void afterDeepNonOptionalSourceOptionalTarget(@MappingTarget Optional<Target.SubType> target,
                                                          Source.SubType source) {
    }

    @BeforeMapping
    default void beforeShallowOptionalSourceWithNoTargetType(Optional<String> source) {
    }

    @BeforeMapping
    default void beforeShallowOptionalSourceWithNonOptionalTargetType(@TargetType Class<String> targetType,
                                                                      Optional<String> source) {
    }

    @AfterMapping
    default void afterShallowOptionalSourceWithNoTarget(Optional<String> source) {

    }

    @AfterMapping
    default void afterShallowOptionalSourceWithNonOptionalTarget(@MappingTarget String target,
                                                                 Optional<String> source) {
    }

    @AfterMapping
    default void afterShallowNonOptionalSourceOptionalTarget(@MappingTarget Optional<String> target, String source) {
    }

}
