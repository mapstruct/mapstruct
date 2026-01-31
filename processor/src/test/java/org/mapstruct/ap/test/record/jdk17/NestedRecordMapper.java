/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.record.jdk17;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 * @author Filip Hrisafov
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface NestedRecordMapper {

    NestedRecordMapper INSTANCE = Mappers.getMapper( NestedRecordMapper.class );

    @Mapping(target = "id", source = "externalId")
    @Mapping(target = "street", source = "address.street")
    @Mapping(target = "city", source = "address.city")
    CareProviderDto map(CareProvider source);

    record Address(String street, String city) {

    }

    record CareProvider(String externalId, Address address) {

    }

    record CareProviderDto(String id, String street, String city) {

    }
}
