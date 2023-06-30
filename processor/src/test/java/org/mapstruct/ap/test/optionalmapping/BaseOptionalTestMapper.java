/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.optionalmapping;

import java.util.Optional;

import org.mapstruct.AfterMapping;
import org.mapstruct.BeforeMapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.TargetType;

public interface BaseOptionalTestMapper {

    Target toTarget(Source source);

    Source fromTarget(Target target);

    @BeforeMapping
    default void beforeOptionalSourceWithNoTargetType(Optional<Source.SubType> source) {
    }

    @BeforeMapping
    default void beforeOptionalSourceWithNonOptionalTargetType(@TargetType Class<Target.SubType> targetType,
                                                               Optional<Source.SubType> source) {
    }

    @BeforeMapping
    default void beforeNonOptionalSourceWithNoTargetType(Source.SubType source) {
    }

    @BeforeMapping
    default void beforeNonOptionalSourceWithNonOptionalTargetType(@TargetType Class<Target.SubType> targetType,
                                                                  Source.SubType source) {
    }

    @AfterMapping
    default void afterOptionalSource(Optional<Source.SubType> source) {
    }

    @AfterMapping
    default void afterNonOptionalSource(Source.SubType source) {
    }

    @AfterMapping
    default void afterOptionalSourceWithNonOptionalTarget(@MappingTarget Target.SubType target,
                                                          Optional<Source.SubType> source) {
    }

    @AfterMapping
    default void afterOptionalSourceWithOptionalTarget(@MappingTarget Optional<Target.SubType> target,
                                                       Optional<Source.SubType> source) {
    }

    @AfterMapping
    default void afterNonOptionalSourceOptionalTarget(@MappingTarget Optional<Target.SubType> target,
                                                      Source.SubType source) {
    }

}
