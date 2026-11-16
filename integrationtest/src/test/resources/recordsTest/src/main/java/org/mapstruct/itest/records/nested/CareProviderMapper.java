/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.itest.records.nested;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 * @author Filip Hrisafov
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface CareProviderMapper {

    CareProviderMapper INSTANCE = Mappers.getMapper( CareProviderMapper.class );

    @Mapping(target = "id", source = "externalId")
    @Mapping(target = "street", source = "address.street")
    @Mapping(target = "city", source = "address.city")
    CareProviderDto map(CareProvider source);
}
