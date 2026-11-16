/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.superbuilder;

public abstract class AbstractVehicle {

    private final int amountOfTires;
    private final Passenger passenger;

    protected AbstractVehicle(AbstractVehicleBuilder<?, ?> b) {
        this.amountOfTires = b.amountOfTires;
        this.passenger = b.passenger;
    }

    public int getAmountOfTires() {
        return this.amountOfTires;
    }

    public Passenger getPassenger() {
        return this.passenger;
    }

    public abstract static class AbstractVehicleBuilder<C extends AbstractVehicle,
        B extends AbstractVehicleBuilder<C, B>> {

        private int amountOfTires;
        private Passenger passenger;

        public B amountOfTires(int amountOfTires) {
            this.amountOfTires = amountOfTires;
            return self();
        }

        public B passenger(Passenger passenger) {
            this.passenger = passenger;
            return self();
        }

        protected abstract B self();

        public abstract C build();
    }
}
