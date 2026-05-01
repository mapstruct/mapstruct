/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.model.common;

import java.util.Set;

/**
 * Model element representing the head of a {@code new T<>(...)} expression. It always renders the diamond
 * operator for generic types, leaving any type-argument inference to the surrounding Java context.
 * <p>
 * The caller is responsible for emitting the parenthesised argument list after the model.
 * <p>
 * Imports contributed by this element only include the raw constructor type, not its type parameters: the
 * generated source never references the parameter classes through this expression, so they are not needed
 * here. If the surrounding code references a type parameter elsewhere (e.g. in a variable declaration or a
 * method signature), that reference contributes its own imports through its own model element.
 *
 * @author Filip Hrisafov
 */
public class NewInstanceCreation extends ModelElement {

    private final Type type;
    private final Type rawType;

    private NewInstanceCreation(Type type) {
        this.type = type;
        this.rawType = type.asRawType();
    }

    /**
     * Creates a {@link NewInstanceCreation} for the given target type. If the target has an implementation
     * type (e.g. {@code Collection} -> {@code ArrayList}), the implementation type is used.
     *
     * @param targetType the target type to be instantiated; must not be {@code null}
     * @return a new model
     */
    public static NewInstanceCreation forType(Type targetType) {
        Type effective = targetType.getImplementationType() != null
            ? targetType.getImplementationType()
            : targetType;
        return new NewInstanceCreation( effective );
    }

    public Type getType() {
        return type;
    }

    public Type getRawType() {
        return rawType;
    }

    public boolean isGeneric() {
        return !type.getTypeParameters().isEmpty();
    }

    @Override
    public Set<Type> getImportTypes() {
        return rawType.getImportTypes();
    }
}
