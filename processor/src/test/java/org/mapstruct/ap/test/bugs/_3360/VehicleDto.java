/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._3360;

/**
 * @author Filip Hrisafov
 */
public abstract class VehicleDto {

    private String name;
    private String model;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public static class Car extends VehicleDto {

        private int numOfDoors;

        public int getNumOfDoors() {
            return numOfDoors;
        }

        public void setNumOfDoors(int numOfDoors) {
            this.numOfDoors = numOfDoors;
        }
    }

}
