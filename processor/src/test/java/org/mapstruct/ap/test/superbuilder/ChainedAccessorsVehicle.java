/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.superbuilder;

public class ChainedAccessorsVehicle {
    private int amountOfTires;
    private Passenger passenger;

    protected ChainedAccessorsVehicle(ChainedAccessorsVehicleBuilder<?, ?> b) {
        this.amountOfTires = b.amountOfTires;
        this.passenger = b.passenger;
    }

    public static ChainedAccessorsVehicleBuilder<?, ?> builder() {
        return new ChainedAccessorsVehicleBuilderImpl();
    }

    public int getAmountOfTires() {
        return this.amountOfTires;
    }

    public Passenger getPassenger() {
        return this.passenger;
    }

    public ChainedAccessorsVehicle setAmountOfTires(int amountOfTires) {
        this.amountOfTires = amountOfTires;
        return this;
    }

    public ChainedAccessorsVehicle setPassenger(Passenger passenger) {
        this.passenger = passenger;
        return this;
    }

    public abstract static class ChainedAccessorsVehicleBuilder<C extends ChainedAccessorsVehicle,
        B extends ChainedAccessorsVehicleBuilder<C, B>> {
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

    private static final class ChainedAccessorsVehicleBuilderImpl
        extends ChainedAccessorsVehicleBuilder<ChainedAccessorsVehicle, ChainedAccessorsVehicleBuilderImpl> {
        private ChainedAccessorsVehicleBuilderImpl() {
        }

        protected ChainedAccessorsVehicleBuilderImpl self() {
            return this;
        }

        public ChainedAccessorsVehicle build() {
            return new ChainedAccessorsVehicle( this );
        }
    }
}
