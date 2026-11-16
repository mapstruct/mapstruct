/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.superbuilder;

public class Vehicle {

    private final int amountOfTires;

    protected Vehicle(VehicleBuilder<?, ?> b) {
        this.amountOfTires = b.amountOfTires;
    }

    public static VehicleBuilder<?, ?> builder() {
        return new VehicleBuilderImpl();
    }

    public int getAmountOfTires() {
        return this.amountOfTires;
    }

    public abstract static class VehicleBuilder<C extends Vehicle, B extends VehicleBuilder<C, B>> {

        private int amountOfTires;

        public B amountOfTires(int amountOfTires) {
            this.amountOfTires = amountOfTires;
            return self();
        }

        protected abstract B self();

        public abstract C build();
    }

    private static final class VehicleBuilderImpl extends VehicleBuilder<Vehicle, VehicleBuilderImpl> {
        private VehicleBuilderImpl() {
        }

        protected VehicleBuilderImpl self() {
            return this;
        }

        public Vehicle build() {
            return new Vehicle( this );
        }
    }
}
