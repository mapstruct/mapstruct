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
 * &#64;Data
 * &#64;SuperBuilder
 * &#64;Accessors(chain = true)
 * &#64;NoArgsConstructor
 * &#64;AllArgsConstructor
 * public class ChainedAccessors {
 *     private int amountOfTires;
 *     private Passenger passenger;
 * }
 * </pre>
 */
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

    // CHECKSTYLE:OFF
    public boolean equals(final Object o) {
        if ( o == this ) {
            return true;
        }
        if ( !( o instanceof ChainedAccessorsVehicle ) ) {
            return false;
        }
        final ChainedAccessorsVehicle other = (ChainedAccessorsVehicle) o;
        if ( !other.canEqual( (Object) this ) ) {
            return false;
        }
        if ( this.getAmountOfTires() != other.getAmountOfTires() ) {
            return false;
        }
        final Object this$passenger = this.getPassenger();
        final Object other$passenger = other.getPassenger();
        if ( this$passenger == null ? other$passenger != null : !this$passenger.equals( other$passenger ) ) {
            return false;
        }
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof ChainedAccessorsVehicle;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        result = result * PRIME + this.getAmountOfTires();
        final Object $passenger = this.getPassenger();
        result = result * PRIME + ( $passenger == null ? 43 : $passenger.hashCode() );
        return result;
    }

    public String toString() {
        return "ChainedAccessorsVehicle(amountOfTires=" + this.getAmountOfTires() + ", passenger=" +
            this.getPassenger() + ")";
    }

    public static abstract class ChainedAccessorsVehicleBuilder<C extends ChainedAccessorsVehicle, B extends ChainedAccessorsVehicleBuilder<C, B>> {
        // CHECKSTYLE:ON
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

        public String toString() {
            return "ChainedAccessorsVehicle.ChainedAccessorsVehicleBuilder(amountOfTires=" + this.amountOfTires +
                ", passenger=" + this.passenger + ")";
        }
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
