/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.superbuilder;

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

    public abstract static class PassengerBuilder<C extends Passenger, B extends PassengerBuilder<C, B>> {

        private String name;

        public B name(String name) {
            this.name = name;
            return self();
        }

        protected abstract B self();

        public abstract C build();
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
