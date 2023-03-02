/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.model;

import java.util.HashSet;
import java.util.Set;

import org.mapstruct.ap.internal.model.common.ModelElement;
import org.mapstruct.ap.internal.model.common.Type;

/**
 * Model element to generate code that initializes a default value for a given type.
 *
 * <p>Uses the provided factory method if available.
 * Otherwise, constructs the target object using a sensible default including but not limited to:
 * - an empty array for array types
 * - an empty collection for collection types
 * - an empty optional for optional types
 * - using the public default constructor for other types if available
 *
 * <p>If no default value can be constructed, a null is returned instead.
 * TODO: Consider throwing an exception instead of returning null.
 *
 * @author Ken Wang
 */
public class InitDefaultValue extends ModelElement {

    private final Type targetType;
    private final MethodReference factoryMethod;

    public InitDefaultValue(Type targetType, MethodReference factoryMethod) {
        this.targetType = targetType;
        this.factoryMethod = factoryMethod;
    }

    @Override
    public Set<Type> getImportTypes() {
        Set<Type> types = new HashSet<>();
        types.add( targetType );
        return types;
    }

    public Type getTargetType() {
        return targetType;
    }

    public MethodReference getFactoryMethod() {
        return factoryMethod;
    }
}
