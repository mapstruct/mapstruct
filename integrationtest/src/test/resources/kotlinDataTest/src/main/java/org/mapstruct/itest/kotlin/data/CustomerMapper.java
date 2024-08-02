/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.itest.kotlin.data;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * @author Filip Hrisafov
 */
@Mapper
public interface CustomerMapper {

    CustomerMapper INSTANCE = Mappers.getMapper( CustomerMapper.class );

    @Mapping(target = "mail", source = "email")
    CustomerEntity fromRecord(CustomerDto record);

    @InheritInverseConfiguration
    CustomerDto toRecord(CustomerEntity entity);

    @Mapping(target = "email", source = "mail")
    CustomerWithOptionalParametersDto mapWithOptionalParameters(CustomerEntity entity);

    @Mapping(target = "email", source = "mail")
    CustomerWithMixedParametersDto mapWithMixedParameters(CustomerEntity entity);
}
