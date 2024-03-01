/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.lombok.superbuilder;

/**
 * Delomboked version of a non-abstract super class using a <code>lombok.experimental.SuperBuilder</code>.
 * <p>
 * <pre>
 * &#64;SuperBuilder
 * &#64;Getter
 * public class Vehicle {
 *     private final int amountOfTires;
 * }
 * </pre>
 */
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

    // CHECKSTYLE:OFF
    public static abstract class VehicleBuilder<C extends Vehicle, B extends VehicleBuilder<C, B>> {
        // CHECKSTYLE:ON
        private int amountOfTires;

        public B amountOfTires(int amountOfTires) {
            this.amountOfTires = amountOfTires;
            return self();
        }

        protected abstract B self();

        public abstract C build();

        public String toString() {
            return "Vehicle.VehicleBuilder(amountOfTires=" + this.amountOfTires + ")";
        }
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
