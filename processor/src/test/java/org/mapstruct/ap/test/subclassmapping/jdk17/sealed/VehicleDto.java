/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.subclassmapping.jdk17.sealed;

public abstract sealed class VehicleDto permits VehicleDto.CarDto, VehicleDto.BikeDto, MotorDto {
    private String name;
    private String maker;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMaker() {
        return maker;
    }

    public void setMaker(String maker) {
        this.maker = maker;
    }

    public static final class BikeDto extends VehicleDto {
        private int numberOfGears;

        public int getNumberOfGears() {
            return numberOfGears;
        }

        public void setNumberOfGears(int numberOfGears) {
            this.numberOfGears = numberOfGears;
        }
    }

    public static final class CarDto extends VehicleDto {
        private boolean manual;

        public boolean isManual() {
            return manual;
        }

        public void setManual(boolean manual) {
            this.manual = manual;
        }
    }
}
