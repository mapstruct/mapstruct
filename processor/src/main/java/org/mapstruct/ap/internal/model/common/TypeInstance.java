/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.model.common;

import java.util.Objects;

import org.mapstruct.ap.internal.util.accessor.Nullability;

/**
 * A {@link Type} bundled with the {@link Nullability} that applies to a particular instance of that
 * type (e.g. a parameter, return type, or field).
 *
 * <p>Nullability is an attribute of how a type is used at a specific point in the model, not of
 * the type itself: the same {@link Type} can appear with different nullabilities in different
 * positions. This object bundles the pair so that the two pieces of information travel together
 * wherever a single instance is the subject (e.g. method parameters, return types).
 */
public final class TypeInstance {

    private final Type type;
    private final Nullability nullability;

    private TypeInstance(Type type, Nullability nullability) {
        this.type = type;
        this.nullability = nullability;
    }

    public static TypeInstance of(Type type, Nullability nullability) {
        return new TypeInstance( type, nullability );
    }

    public Type getType() {
        return type;
    }

    public Nullability getNullability() {
        return nullability;
    }

    @Override
    public boolean equals(Object o) {
        if ( this == o ) {
            return true;
        }
        if ( o == null || getClass() != o.getClass() ) {
            return false;
        }
        TypeInstance that = (TypeInstance) o;
        return Objects.equals( type, that.type ) && Objects.equals( nullability, that.nullability );
    }

    @Override
    public int hashCode() {
        return Objects.hash( type, nullability );
    }

    @Override
    public String toString() {
        return "TypeInstance{ type=" + type + ", nullability=" + nullability + " }";
    }
}
