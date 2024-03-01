/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.lombok.superbuilder;

/**
 * Delomboked version of a class inheriting a non-abstract super class
 * via <code>lombok.experimental.SuperBuilder</code>.
 * <p>
 * <pre>
 * &#64;SuperBuilder
 * &#64;Getter
 * public class Car extends Vehicle {
 *     private final String manufacturer;
 *     private final Passenger passenger;
 * }
 * </pre>
 */
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

    // CHECKSTYLE:OFF
    public static abstract class CarBuilder<C extends Car, B extends CarBuilder<C, B>> extends VehicleBuilder<C, B> {
        // CHECKSTYLE:ON
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

        public String toString() {
            return "Car.CarBuilder(super=" + super.toString() + ", manufacturer=" + this.manufacturer + ", passenger=" +
                this.passenger + ")";
        }
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
