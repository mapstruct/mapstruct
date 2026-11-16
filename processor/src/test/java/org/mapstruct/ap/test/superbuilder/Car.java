/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.superbuilder;

public class Car extends Vehicle {

    private final String manufacturer;
    private final Passenger passenger;

    protected Car(CarBuilder<?, ?> b) {
        super( b );
        this.manufacturer = b.manufacturer;
        this.passenger = b.passenger;
    }

    public static CarBuilder<?, ?> builder() {
        return new CarBuilderImpl();
    }

    public String getManufacturer() {
        return this.manufacturer;
    }

    public Passenger getPassenger() {
        return this.passenger;
    }

    public abstract static class CarBuilder<C extends Car, B extends CarBuilder<C, B>> extends VehicleBuilder<C, B> {

        private String manufacturer;
        private Passenger passenger;

        public B manufacturer(String manufacturer) {
            this.manufacturer = manufacturer;
            return self();
        }

        public B passenger(Passenger passenger) {
            this.passenger = passenger;
            return self();
        }

        protected abstract B self();

        public abstract C build();
    }

    private static final class CarBuilderImpl extends CarBuilder<Car, CarBuilderImpl> {
        private CarBuilderImpl() {
        }

        protected CarBuilderImpl self() {
            return this;
        }

        public Car build() {
            return new Car( this );
        }
    }
}
