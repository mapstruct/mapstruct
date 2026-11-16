/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.subclassmapping.jdk17.sealed;

public abstract sealed class Vehicle permits Motor, Vehicle.Bike, Vehicle.Car {
    private String name;
    private String vehicleManufacturingCompany;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVehicleManufacturingCompany() {
        return vehicleManufacturingCompany;
    }

    public void setVehicleManufacturingCompany(String vehicleManufacturingCompany) {
        this.vehicleManufacturingCompany = vehicleManufacturingCompany;
    }

    public static final class Bike extends Vehicle {
        private int numberOfGears;

        public int getNumberOfGears() {
            return numberOfGears;
        }

        public void setNumberOfGears(int numberOfGears) {
            this.numberOfGears = numberOfGears;
        }
    }

    public static final class Car extends Vehicle {
        private boolean manual;

        public boolean isManual() {
            return manual;
        }

        public void setManual(boolean manual) {
            this.manual = manual;
        }
    }
}
