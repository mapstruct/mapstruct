/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.constructor.deprecated;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * Only the mapping between the lists is declared, so the mapping of the elements has to be generated. That is where
 * the compilation fails when the deprecated parameterless constructor is used for {@link AddressEntity}, because the
 * entity has no write accessors at all then.
 *
 * @author Oleg Babichev
 */
@Mapper
public interface AddressEntityMapper {

    AddressEntityMapper INSTANCE = Mappers.getMapper( AddressEntityMapper.class );

    List<AddressEntity> map(List<AddressDto> dtos);
}
