/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.itest.records;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 * @author Filip Hrisafov
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface CarAndWheelMapper {

    CarAndWheelMapper INSTANCE = Mappers.getMapper( CarAndWheelMapper.class );

    default String stringFromWheelPosition(WheelPosition source) {
        return source == null ? null : source.getPosition();
    }

    default WheelPosition wheelPositionFromString(String source) {
        return source == null ? null : new WheelPosition(source);
    }

    CarDto carDtoFromCar(Car source);

    Car carFromCarDto(CarDto source);
}
