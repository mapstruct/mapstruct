/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.superbuilder;

public class MuscleCar extends Car {

    private final float horsePower;

    protected MuscleCar(MuscleCarBuilder<?, ?> b) {
        super( b );
        this.horsePower = b.horsePower;
    }

    public static MuscleCarBuilder<?, ?> builder() {
        return new MuscleCarBuilderImpl();
    }

    public float getHorsePower() {
        return this.horsePower;
    }

    public abstract static class MuscleCarBuilder<C extends MuscleCar, B extends MuscleCarBuilder<C, B>>
        extends CarBuilder<C, B> {

        private float horsePower;

        public B horsePower(float horsePower) {
            this.horsePower = horsePower;
            return self();
        }

        protected abstract B self();

        public abstract C build();
    }

    private static final class MuscleCarBuilderImpl extends MuscleCarBuilder<MuscleCar, MuscleCarBuilderImpl> {
        private MuscleCarBuilderImpl() {
        }

        protected MuscleCarBuilderImpl self() {
            return this;
        }

        public MuscleCar build() {
            return new MuscleCar( this );
        }
    }
}
