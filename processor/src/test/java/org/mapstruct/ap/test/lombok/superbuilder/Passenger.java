/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.lombok.superbuilder;

/**
 * Delomboked version of a class without inheritance using a <code>lombok.experimental.SuperBuilder</code>.
 * <p>
 * <pre>
 * &#64;SuperBuilder
 * &#64;Getter
 * public class Passenger {
 *     private final String name;
 * }
 * </pre>
 */
public class Passenger {

    private final String name;

    protected Passenger(PassengerBuilder<?, ?> b) {
        this.name = b.name;
    }

    public static PassengerBuilder<?, ?> builder() {
        return new PassengerBuilderImpl();
    }

    public String getName() {
        return this.name;
    }

    // CHECKSTYLE:OFF
    public static abstract class PassengerBuilder<C extends Passenger, B extends PassengerBuilder<C, B>> {
        // CHECKSTYLE:ON
        private String name;

        public B name(String name) {
            this.name = name;
            return self();
        }

        protected abstract B self();

        public abstract C build();

        public String toString() {
            return "Passenger.PassengerBuilder(name=" + this.name + ")";
        }
    }

    private static final class PassengerBuilderImpl extends PassengerBuilder<Passenger, PassengerBuilderImpl> {
        private PassengerBuilderImpl() {
        }

        protected PassengerBuilderImpl self() {
            return this;
        }

        public Passenger build() {
            return new Passenger( this );
        }
    }
}
