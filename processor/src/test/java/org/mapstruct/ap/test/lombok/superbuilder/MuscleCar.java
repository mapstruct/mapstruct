/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.lombok.superbuilder;

/**
 * Delomboked version of a class inheriting an intermediate super class
 * via <code>lombok.experimental.SuperBuilder</code>.
 * <p>
 * <pre>
 * &#64;SuperBuilder
 * &#64;Getter
 * public class MuscleCar extends Car {
 *     private final float horsePower;
 * }
 * </pre>
 */
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

    // CHECKSTYLE:OFF
    public static abstract class MuscleCarBuilder<C extends MuscleCar, B extends MuscleCarBuilder<C, B>> extends CarBuilder<C, B> {
        // CHECKSTYLE:ON
        private float horsePower;

        public B horsePower(float horsePower) {
            this.horsePower = horsePower;
            return self();
        }

        protected abstract B self();

        public abstract C build();

        public String toString() {
            return "MuscleCar.MuscleCarBuilder(super=" + super.toString() + ", horsePower=" + this.horsePower + ")";
        }
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
