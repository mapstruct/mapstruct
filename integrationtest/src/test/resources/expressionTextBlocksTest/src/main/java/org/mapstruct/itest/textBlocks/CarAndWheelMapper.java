/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.itest.textBlocks;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 * @author Filip Hrisafov
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface CarAndWheelMapper {

    CarAndWheelMapper INSTANCE = Mappers.getMapper( CarAndWheelMapper.class );

    @Mapping(target = "wheelPosition",
        expression =
            """
                java(
                    source.getWheelPosition() == null ?
                        null :
                        source.getWheelPosition().getPosition()
                )
            """)
    CarDto carDtoFromCar(Car source);

    @Mapping(target = "wheelPosition",
        expression = """
                java(
                    source.wheelPosition() == null ?
                        null :
                        new WheelPosition(source.wheelPosition())
                )
            """)
    Car carFromCarDto(CarDto source);
}
