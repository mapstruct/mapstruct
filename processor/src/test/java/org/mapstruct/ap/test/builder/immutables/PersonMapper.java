/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.builder.immutables;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * Mapper exercising the {@code @Value.Immutable} path in
 * {@link org.mapstruct.ap.spi.ImmutablesBuilderProvider}: MapStruct should redirect from the annotated
 * interface {@link Person} to the generated {@link ImmutablePerson} and use its nested {@code Builder}.
 */
@Mapper
public interface PersonMapper {

    PersonMapper INSTANCE = Mappers.getMapper( PersonMapper.class );

    Person toPerson(PersonDto dto);
}
