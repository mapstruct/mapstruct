/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.superbuilder;

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

    public abstract static class ChainedAccessorsCarBuilder<C extends ChainedAccessorsCar,
        B extends ChainedAccessorsCarBuilder<C, B>> extends ChainedAccessorsVehicleBuilder<C, B> {

        private String manufacturer;

        public B manufacturer(String manufacturer) {
            this.manufacturer = manufacturer;
            return self();
        }

        protected abstract B self();

        public abstract C build();
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
