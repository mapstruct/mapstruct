/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.lombok.superbuilder;

/**
 * Delomboked version of a class inheriting an abstract super class via <code>lombok.experimental.SuperBuilder</code>.
 * <p>
 * <pre>
 * &#64;SuperBuilder
 * &#64;Getter
 * public class InheritedAbstractCar extends AbstractVehicle {
 *     private final String manufacturer;
 * }
 * </pre>
 */
public class InheritedAbstractCar extends AbstractVehicle {

    private final String manufacturer;

    protected InheritedAbstractCar(InheritedAbstractCarBuilder<?, ?> b) {
        super( b );
        this.manufacturer = b.manufacturer;
    }

    public static InheritedAbstractCarBuilder<?, ?> builder() {
        return new InheritedAbstractCarBuilderImpl();
    }

    public String getManufacturer() {
        return this.manufacturer;
    }

    // CHECKSTYLE:OFF
    public static abstract class InheritedAbstractCarBuilder<C extends InheritedAbstractCar, B extends InheritedAbstractCarBuilder<C, B>>
        // CHECKSTYLE:ON
        extends AbstractVehicleBuilder<C, B> {
        private String manufacturer;

        public B manufacturer(String manufacturer) {
            this.manufacturer = manufacturer;
            return self();
        }

        protected abstract B self();

        public abstract C build();

        public String toString() {
            return "InheritedAbstractCar.InheritedAbstractCarBuilder(super=" + super.toString() + ", manufacturer=" +
                this.manufacturer + ")";
        }
    }

    private static final class InheritedAbstractCarBuilderImpl
        extends InheritedAbstractCarBuilder<InheritedAbstractCar, InheritedAbstractCarBuilderImpl> {
        private InheritedAbstractCarBuilderImpl() {
        }

        protected InheritedAbstractCarBuilderImpl self() {
            return this;
        }

        public InheritedAbstractCar build() {
            return new InheritedAbstractCar( this );
        }
    }
}
