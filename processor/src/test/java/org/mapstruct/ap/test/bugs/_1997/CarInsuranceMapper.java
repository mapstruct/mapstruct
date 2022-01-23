/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1997;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

/**
 * @author Filip Hrisafov
 */
@Mapper
public interface CarInsuranceMapper {

    CarInsuranceMapper INSTANCE = Mappers.getMapper( CarInsuranceMapper.class );

    @Mapping(source = "model", target = "detail.model")
    void update(Car source, @MappingTarget CarInsurance target);
}
