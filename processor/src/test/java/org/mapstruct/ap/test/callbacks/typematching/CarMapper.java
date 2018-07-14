/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.callbacks.typematching;

import org.mapstruct.AfterMapping;
import org.mapstruct.BeforeMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

/**
 * @author Andreas Gudian
 *
 */
@Mapper
public abstract class CarMapper {

    public static final CarMapper INSTANCE = Mappers.getMapper( CarMapper.class );

    public abstract CarEntity toCarEntity(CarDto carDto);

    @AfterMapping
    protected void neverMatched(ElectricCarDto electricDto) {
        throw new RuntimeException( "must not be called" );
    }

    @AfterMapping
    protected void neverMatched(@MappingTarget ElectricCarEntity electricEntity) {
        throw new RuntimeException( "must not be called" );
    }

    @AfterMapping
    protected void isCalled(@MappingTarget Object any) {
        if ( any instanceof CarEntity ) {
            CarEntity car = (CarEntity) any;
            if ( car.getSeatCount() == 0 ) {
                car.setSeatCount( 5 );
            }
        }
    }

    @AfterMapping
    protected void incrementsTargetId(@MappingTarget Identifiable identifiable) {
        identifiable.setId( identifiable.getId() + 1 );
    }

    @BeforeMapping
    protected void incrementsSourceId(Identifiable identifiable) {
        identifiable.setId( identifiable.getId() + 1 );
    }

    public abstract static class Identifiable {
        private long id;

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }
    }

    public static class CarDto extends Identifiable {
        private int seatCount;

        public int getSeatCount() {
            return seatCount;
        }

        public void setSeatCount(int seatCount) {
            this.seatCount = seatCount;
        }
    }

    public static class ElectricCarDto extends CarDto {
        private long batteryCapacity;

        public long getBatteryCapacity() {
            return batteryCapacity;
        }

        public void setBatteryCapacity(long batteryCapacity) {
            this.batteryCapacity = batteryCapacity;
        }
    }

    public static class CarEntity extends Identifiable {
        private int seatCount;

        public int getSeatCount() {
            return seatCount;
        }

        public void setSeatCount(int seatCount) {
            this.seatCount = seatCount;
        }
    }

    public static class ElectricCarEntity extends Identifiable {
        private long batteryCapacity;

        public long getBatteryCapacity() {
            return batteryCapacity;
        }

        public void setBatteryCapacity(long batteryCapacity) {
            this.batteryCapacity = batteryCapacity;
        }
    }
}
