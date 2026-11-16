/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nullcheck.jspecify;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

/**
 * Verifies that existing safety guards still trigger a null check when the source is
 * {@code @Nullable}:
 * <ul>
 *   <li>Unboxing ({@code Integer} -&gt; {@code int})</li>
 *   <li>{@code defaultValue}</li>
 *   <li>{@link NullValuePropertyMappingStrategy#SET_TO_DEFAULT} on an update method</li>
 * </ul>
 */
@Mapper
public interface JSpecifySafetyGuardMapper {

    JSpecifySafetyGuardMapper INSTANCE = Mappers.getMapper( JSpecifySafetyGuardMapper.class );

    @Mapping(target = "unboxedNumber", source = "nullableNumber")
    @Mapping(target = "textWithDefault", source = "nullableText", defaultValue = "default")
    @Mapping(target = "textWithNvpms", ignore = true)
    SafetyGuardTargetBean map(SafetyGuardSourceBean source);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_DEFAULT)
    @Mapping(target = "unboxedNumber", ignore = true)
    @Mapping(target = "textWithDefault", ignore = true)
    @Mapping(target = "textWithNvpms", source = "nullableText")
    void update(SafetyGuardSourceBean source, @MappingTarget SafetyGuardTargetBean target);
}
