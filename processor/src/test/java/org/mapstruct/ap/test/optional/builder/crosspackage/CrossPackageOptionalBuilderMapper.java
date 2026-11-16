/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.optional.builder.crosspackage;

import java.util.Optional;

import org.mapstruct.Mapper;
import org.mapstruct.ap.test.optional.builder.crosspackage.dto.PersonTarget;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CrossPackageOptionalBuilderMapper {

    CrossPackageOptionalBuilderMapper INSTANCE = Mappers.getMapper( CrossPackageOptionalBuilderMapper.class );

    Optional<PersonTarget> toTarget(PersonSource source);
}
