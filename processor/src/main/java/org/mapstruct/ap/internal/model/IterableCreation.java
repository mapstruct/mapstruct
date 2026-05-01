/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.model;

import java.util.HashSet;
import java.util.Set;

import org.mapstruct.ap.internal.model.common.ModelElement;
import org.mapstruct.ap.internal.model.common.NewInstanceCreation;
import org.mapstruct.ap.internal.model.common.Parameter;
import org.mapstruct.ap.internal.model.common.Type;

import static org.mapstruct.ap.internal.util.Collections.first;

/**
 * Model element that can be used to create a type of {@link Iterable} or {@link java.util.Map}. If an implementation
 * type is used and the target type has a constructor with {@code int} as parameter and the source parameter is of
 * {@link java.util.Collection}, {@link java.util.Map} or {@code Array} type then MapStruct will use that constructor
 * with the {@code size} / {@code length} from the source parameter.
 *
 * @author Filip Hrisafov
 */
public class IterableCreation extends ModelElement {

    private final Type resultType;
    private final Parameter sourceParameter;
    private final MethodReference factoryMethod;
    private final NewInstanceCreation newInstance;
    private final boolean canUseSize;
    private final boolean loadFactorAdjustment;

    private IterableCreation(Type resultType, Parameter sourceParameter, MethodReference factoryMethod) {
        this.resultType = resultType;
        this.sourceParameter = sourceParameter;
        this.factoryMethod = factoryMethod;
        this.newInstance = factoryMethod == null ? NewInstanceCreation.forType( resultType ) : null;
        this.canUseSize = ( sourceParameter.getType().isCollectionOrMapType() ||
            sourceParameter.getType().isArrayType() )
            && resultType.getImplementation() != null && resultType.getImplementation().hasInitialCapacityConstructor();
        this.loadFactorAdjustment = this.canUseSize && resultType.getImplementation().isLoadFactorAdjustment();

    }

    public static IterableCreation create(NormalTypeMappingMethod mappingMethod, Parameter sourceParameter) {
        return new IterableCreation( mappingMethod.getResultType(), sourceParameter, mappingMethod.getFactoryMethod() );
    }

    public Type getResultType() {
        return resultType;
    }

    public Parameter getSourceParameter() {
        return sourceParameter;
    }

    public MethodReference getFactoryMethod() {
        return this.factoryMethod;
    }

    public NewInstanceCreation getNewInstance() {
        return newInstance;
    }

    public boolean isCanUseSize() {
        return canUseSize;
    }

    public boolean isLoadFactorAdjustment() {
        return loadFactorAdjustment;
    }

    @Override
    public Set<Type> getImportTypes() {
        Set<Type> types = new HashSet<>();
        if ( newInstance != null ) {
            types.addAll( newInstance.getImportTypes() );
        }

        if ( isEnumSet() ) {
            types.add( getEnumSetElementType() );
            // The result type itself is an EnumSet
            types.add( resultType );
        }
        return types;
    }

    public Type getEnumSetElementType() {
        return first( getResultType().determineTypeArguments( Iterable.class ) );
    }

    public boolean isEnumSet() {
        return "java.util.EnumSet".equals( resultType.getFullyQualifiedName() );
    }
}
