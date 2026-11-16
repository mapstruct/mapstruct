/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.record.jdk17;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author Filip Hrisafov
 */
@Mapper
public interface RecordWithListMapper {

    RecordWithListMapper INSTANCE = Mappers.getMapper( RecordWithListMapper.class );

    default String stringFromWheelPosition(WheelPosition source) {
        return source == null ? null : source.getPosition();
    }

    default WheelPosition wheelPositionFromString(String source) {
        return source == null ? null : new WheelPosition( source );
    }

    CarDto carDtoFromCar(Car source);

    Car carFromCarDto(CarDto source);

    record CarDto(List<String> wheelPositions) {

    }

    class Car {

        private List<WheelPosition> wheelPositions;

        public List<WheelPosition> getWheelPositions() {
            return wheelPositions;
        }

        public void setWheelPositions(List<WheelPosition> wheelPositions) {
            this.wheelPositions = wheelPositions;
        }
    }

    class WheelPosition {

        private final String position;

        public WheelPosition(String position) {
            this.position = position;
        }

        public String getPosition() {
            return position;
        }
    }
}
