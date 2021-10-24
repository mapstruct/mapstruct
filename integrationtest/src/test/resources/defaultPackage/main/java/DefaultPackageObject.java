/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * @author Filip Hrisafov
 */
public class DefaultPackageObject {
    public enum CarType {
        SEDAN, CAMPER, X4, TRUCK;
    }

    static public class Car {

        private String make;
        private int numberOfSeats;
        private CarType type;

        public Car(String string, int numberOfSeats, CarType sedan) {
            this.make = string;
            this.numberOfSeats = numberOfSeats;
            this.type = sedan;
        }


        public String getMake() {
            return make;
        }

        public void setMake(String make) {
            this.make = make;
        }

        public int getNumberOfSeats() {
            return numberOfSeats;
        }

        public void setNumberOfSeats(int numberOfSeats) {
            this.numberOfSeats = numberOfSeats;
        }

        public CarType getType() {
            return type;
        }

        public void setType(CarType type) {
            this.type = type;
        }
    }

    static public class CarDto {

        private String make;
        private int seatCount;
        private String type;

        public String getMake() {
            return make;
        }

        public void setMake(String make) {
            this.make = make;
        }

        public int getSeatCount() {
            return seatCount;
        }

        public void setSeatCount(int seatCount) {
            this.seatCount = seatCount;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }


    @Mapper
    public interface CarMapper {

        CarMapper INSTANCE = Mappers.getMapper( CarMapper.class );

        @Mapping(source = "numberOfSeats", target = "seatCount")
        CarDto carToCarDto(Car car);
    }
}
