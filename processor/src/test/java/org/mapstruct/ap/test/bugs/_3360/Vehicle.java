/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._3360;

/**
 * @author Filip Hrisafov
 */
public abstract class Vehicle {

    private final String name;
    private final String modelName;

    protected Vehicle(String name, String modelName) {
        this.name = name;
        this.modelName = modelName;
    }

    public String getName() {
        return name;
    }

    public String getModelName() {
        return modelName;
    }

    public String getComputedName() {
        return null;
    }

    public static class Car extends Vehicle {

        private final int numOfDoors;

        public Car(String name, String modelName, int numOfDoors) {
            super( name, modelName );
            this.numOfDoors = numOfDoors;
        }

        public int getNumOfDoors() {
            return numOfDoors;
        }
    }

    public static class Motorbike extends Vehicle {

        public Motorbike(String name, String modelName) {
            super( name, modelName );
        }

    }

}
