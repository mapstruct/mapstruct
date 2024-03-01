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
 * &#64;EqualsAndHashCode(callSuper = true)
 * &#64;Data
 * &#64;SuperBuilder
 * &#64;Accessors(chain = true)
 * public class ChainedAccessorsCar extends Vehicle {
 *     private String manufacturer;
 * }
 * </pre>
 */
public class ChainedAccessorsCar extends ChainedAccessorsVehicle {

    private String manufacturer;

    protected ChainedAccessorsCar(ChainedAccessorsCarBuilder<?, ?> b) {
        super( b );
        this.manufacturer = b.manufacturer;
    }

    public static ChainedAccessorsCarBuilder<?, ?> builder() {
        return new ChainedAccessorsCarBuilderImpl();
    }

    public String getManufacturer() {
        return this.manufacturer;
    }

    public ChainedAccessorsCar setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
        return this;
    }

    public String toString() {
        return "ChainedAccessorsCar(manufacturer=" + this.getManufacturer() + ")";
    }

    public boolean equals(final Object o) {
        if ( o == this ) {
            return true;
        }
        if ( !( o instanceof ChainedAccessorsCar ) ) {
            return false;
        }
        final ChainedAccessorsCar other = (ChainedAccessorsCar) o;
        if ( !other.canEqual( (Object) this ) ) {
            return false;
        }
        if ( !super.equals( o ) ) {
            return false;
        }
        // CHECKSTYLE:OFF
        final Object this$manufacturer = this.getManufacturer();
        final Object other$manufacturer = other.getManufacturer();
        if ( this$manufacturer == null ? other$manufacturer != null :
            !this$manufacturer.equals( other$manufacturer ) ) {
            return false;
        }
        // CHECKSTYLE:ON
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof ChainedAccessorsCar;
    }

    // CHECKSTYLE:OFF
    public int hashCode() {
        final int PRIME = 59;
        int result = super.hashCode();
        final Object $manufacturer = this.getManufacturer();
        result = result * PRIME + ( $manufacturer == null ? 43 : $manufacturer.hashCode() );
        return result;
    }

    public static abstract class ChainedAccessorsCarBuilder<C extends ChainedAccessorsCar, B extends ChainedAccessorsCarBuilder<C, B>> extends ChainedAccessorsVehicleBuilder<C, B> {
        // CHECKSTYLE:ON
        private String manufacturer;

        public B manufacturer(String manufacturer) {
            this.manufacturer = manufacturer;
            return self();
        }

        protected abstract B self();

        public abstract C build();

        public String toString() {
            return "ChainedAccessorsCar.ChainedAccessorsCarBuilder(super=" + super.toString() + ", manufacturer=" +
                this.manufacturer + ")";
        }
    }

    private static final class ChainedAccessorsCarBuilderImpl
        extends ChainedAccessorsCarBuilder<ChainedAccessorsCar, ChainedAccessorsCarBuilderImpl> {
        private ChainedAccessorsCarBuilderImpl() {
        }

        protected ChainedAccessorsCarBuilderImpl self() {
            return this;
        }

        public ChainedAccessorsCar build() {
            return new ChainedAccessorsCar( this );
        }
    }
}
