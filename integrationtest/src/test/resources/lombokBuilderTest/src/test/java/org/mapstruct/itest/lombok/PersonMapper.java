/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.itest.lombok;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 * The Builder creation method is not present during the annotation processing of the mapper.
 * Therefore we put the mapper into a separate class.
 *
 * @see <span>Lombok Issue <a href="https://github.com/rzwitserloot/lombok/issues/1538">#1538</a></span>
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface PersonMapper {

    PersonMapper INSTANCE = Mappers.getMapper( PersonMapper.class );

    Person fromDto(PersonDto personDto);
    PersonDto toDto(Person personDto);
}
