/**
 *  Copyright 2012-2015 Gunnar Morling (http://www.gunnarmorling.de/)
 *  and/or other contributors as indicated by the @authors tag. See the
 *  copyright.txt file in the distribution for a full listing of all
 *  contributors.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
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
