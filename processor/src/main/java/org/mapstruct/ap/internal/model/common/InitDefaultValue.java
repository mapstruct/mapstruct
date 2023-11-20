package org.mapstruct.ap.internal.model.common;

import java.util.HashSet;
import java.util.Set;

import org.mapstruct.ap.internal.model.MethodReference;

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
