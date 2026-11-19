/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nullcheck.jspecify;

import org.jspecify.annotations.Nullable;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

/**
 * Reproduces the update-method behavior of the two-step
 * {@link org.mapstruct.ap.internal.model.NullSafe2StepMappingMethod} path for a <b>primitive</b> target.
 * <p>
 * The nullable source property {@code name} is a {@code String}. It is converted by the user method
 * {@code stringToInteger} into the nullable intermediate {@code Integer}, from which the built-in
 * {@code Integer -> long} conversion produces the primitive {@code long} target.
 * <p>
 * This is the same shape as {@link JSpecifyNullSafe2StepPrimitiveMapper}, but exposed as an
 * {@code @MappingTarget} update method so the behavior for an <em>existing</em> target value can be
 * observed.
 */
@Mapper
public interface JSpecifyNullSafe2StepPrimitiveUpdateMapper {

    JSpecifyNullSafe2StepPrimitiveUpdateMapper INSTANCE =
        Mappers.getMapper( JSpecifyNullSafe2StepPrimitiveUpdateMapper.class );

    @Named("stringToInteger")
    @Nullable
    default Integer stringToInteger(@Nullable String name) {
        return name == null ? null : Integer.valueOf( name );
    }

    @Mapping(target = "number", source = "name", qualifiedByName = "stringToInteger")
    void update(JSpecifyNullSafe2StepSourceBean source,
                @MappingTarget JSpecifyNullSafe2StepPrimitiveTargetBean target);
}
