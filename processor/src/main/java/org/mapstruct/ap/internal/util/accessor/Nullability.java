/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.util.accessor;

import java.util.Objects;
import java.util.Optional;
import javax.lang.model.type.TypeMirror;

/**
 * Represents the {@link Nullability} of a type. A {@link org.mapstruct.ap.internal.model.common.Type Type} of its own
 * does not have a nullability. That's why nullability has to be determined for every instance of a
 * {@link org.mapstruct.ap.internal.model.common.Type Type}. In MapStruct these instances are:
 * <ul>
 *     <li>Method {@link org.mapstruct.ap.internal.model.common.Parameter Parameters}</li>
 *     <li>Return Types of a {@link org.mapstruct.ap.internal.model.source.Method Method}</li>
 *     <li>Fields</li>
 * </ul>
 * A {@link org.mapstruct.ap.internal.model.common.TypeInstance TypeInstance} can be used to carry a {@code Type}
 * together with its {@link Nullability} when the two need to travel as a pair.
 * Between a source and a target instance MapStruct has to ensure that the {@link Nullability} requirements are met.
 * For this, MapStruct can adjust the code it generates to perform or skip null checks and to adjust default return
 * values. The key requirement is that MapStruct always fulfils the {@link Nullability} contract. This does not mean
 * that a generated method cannot exceed its contract.
 * <br>
 * A {@link Nullability} carries two pieces of information:
 * <ul>
 *     <li>{@link Nullability.NullabilityState}: whether a type instance can be null
 *     ({@link NullabilityState#NULLABLE}) or not ({@link NullabilityState#NON_NULL})</li>
 *     <li>{@link Nullability.NullabilityCause}: how that state was derived. A special case is
 *      {@link NullabilityCause#VOID}, which represents a {@code void} method where the return type does not need a
 *      nullability check.</li>
 * </ul>
 *
 * @author hduelme
 */
public class Nullability {

    private final NullabilityState state;
    private final NullabilityCause cause;

    Nullability(NullabilityState state, NullabilityCause cause) {
        this.state = state;
        this.cause = cause;
    }

    public static Nullability hardcodedNullability(NullabilityState state) {
        return new Nullability( state, NullabilityCause.HARDCODED );
    }

    public static Nullability voidNullability() {
        return new Nullability( NullabilityState.NON_NULL, NullabilityCause.VOID );
    }

    /**
     * Resolves the nullability for a primitive type.
     *
     * @param typeMirror the type to inspect
     * @return an {@code Optional} containing a {@code (NON_NULL, PRIMITIVE)} nullability if the type is a
     *         primitive, otherwise an empty {@code Optional} (including for a {@code null} type)
     */
    public static Optional<Nullability> getPrimitiveNullability( TypeMirror typeMirror ) {
        if ( typeMirror != null && typeMirror.getKind().isPrimitive() ) {
            return Optional.of( new Nullability( NullabilityState.NON_NULL, NullabilityCause.PRIMITIVE ) );
        }
        return Optional.empty();
    }

    public enum NullabilityState {
        /**
         * Represents that a type instance can have {@code null} as a value
         */
        NULLABLE,
        /**
         * Represents that a type instance can never be {@code null}.
         */
        NON_NULL
    }

    public enum NullabilityCause {
        /**
         * Represents a known {@link Nullability} hardcoded into MapStruct
         */
        HARDCODED,
        /**
         * Represents a {@link Nullability} caused by JSpecify annotations.
         * See <a href="https://jspecify.dev">https://jspecify.dev</a>.
         */
        JSPECIFY,
        /**
         * Represents the method's {@link Nullability} caused by a configured default return type.
         * Always {@link NullabilityState#NON_NULL}
         */
        RETURN_DEFAULT_VALUE,
        /**
         * Represents the default Java {@link Nullability} of an Object. Always {@link NullabilityState#NULLABLE}
         */
        DEFAULT,
        /**
         * Represents the {@link Nullability} of a primitive type ({@code boolean, byte, short, int, long, float,
         * double, char}). Always {@link NullabilityState#NON_NULL}
         */
        PRIMITIVE,
        /**
         * Represents the {@link Nullability} of a {@code void} method. This should not need to be checked.
         * Always {@link NullabilityState#NON_NULL}
         */
        VOID
    }

    public boolean isNullable() {
        return state == NullabilityState.NULLABLE;
    }

    public boolean isNonNullable() {
        return state == NullabilityState.NON_NULL;
    }

    public NullabilityState getState() {
        return state;
    }

    public NullabilityCause getCause() {
        return cause;
    }

    public Nullability chain(Nullability parent) {
        if ( parent.state == NullabilityState.NULLABLE ) {
            return parent;
        }
        return this;
    }

    public Nullability withIsReturnDefault(boolean isReturnDefault) {
        if ( isReturnDefault && this.state == NullabilityState.NULLABLE ) {
            return new Nullability( NullabilityState.NON_NULL, NullabilityCause.RETURN_DEFAULT_VALUE );
        }
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if ( o == null || getClass() != o.getClass() ) {
            return false;
        }
        Nullability that = (Nullability) o;
        return state == that.state && cause == that.cause;
    }

    @Override
    public int hashCode() {
        return Objects.hash( state, cause );
    }

    @Override
    public String toString() {
        return "Nullability{" +
                "state=" + state +
                ", cause=" + cause +
                '}';
    }
}
