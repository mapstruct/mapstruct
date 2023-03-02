/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.model;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.model.source.Method;

/**
 * A {@link MappingMethod} that is used by the main mapping methods ({@link BeanMappingMethod},
 * {@link MapMappingMethod}, {@link IterableMappingMethod} and {@link StreamMappingMethod} (non-enum / non-value
 * mapping)
 *
 * @author Filip Hrisafov
 */
public abstract class NormalTypeMappingMethod extends MappingMethod {
    private final MethodReference factoryMethod;
    private final boolean mapNullToDefault;

    private final List<Annotation> annotations;

    NormalTypeMappingMethod(Method method, List<Annotation> annotations,
        Collection<String> existingVariableNames, MethodReference factoryMethod,
        boolean mapNullToDefault,
        List<LifecycleCallbackMethodReference> beforeMappingReferences,
        List<LifecycleCallbackMethodReference> afterMappingReferences) {
        super( method, existingVariableNames, beforeMappingReferences, afterMappingReferences );
        this.factoryMethod = factoryMethod;
        this.mapNullToDefault = mapNullToDefault;
        this.annotations = annotations;
    }

    @Override
    public Set<Type> getImportTypes() {
        Set<Type> types = super.getImportTypes();
        if ( ( factoryMethod == null ) && ( !isExistingInstanceMapping() ) ) {
            if ( getReturnType().getImplementationType() != null ) {
                types.addAll( getReturnType().getImplementationType().getImportTypes() );
            }
        }
        else if ( factoryMethod != null ) {
            types.addAll( factoryMethod.getImportTypes() );
        }
        for ( Annotation annotation : annotations ) {
            types.addAll( annotation.getImportTypes() );
        }
        return types;
    }

    public boolean isMapNullToDefault() {
        return mapNullToDefault;
    }

    public MethodReference getFactoryMethod() {
        return this.factoryMethod;
    }

    public InitDefaultValue getInitDefaultValueForResultType() {
        return new InitDefaultValue( this.getResultType(), this.getFactoryMethod() );
    }

    public List<Annotation> getAnnotations() {
        return annotations;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ( ( getResultType() == null ) ? 0 : getResultType().hashCode() );
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if ( this == obj ) {
            return true;
        }
        if ( obj == null ) {
            return false;
        }
        if ( getClass() != obj.getClass() ) {
            return false;
        }
        NormalTypeMappingMethod other = (NormalTypeMappingMethod) obj;

        if ( !super.equals( obj ) ) {
            return false;
        }

        if ( !getResultType().equals( other.getResultType() ) ) {
            return false;
        }

        if ( getSourceParameters().size() != other.getSourceParameters().size() ) {
            return false;
        }

        for ( int i = 0; i < getSourceParameters().size(); i++ ) {
            if ( !getSourceParameters().get( i ).getType().equals( other.getSourceParameters().get( i ).getType() ) ) {
                return false;
            }
            List<Type> thisTypeParameters = getSourceParameters().get( i ).getType().getTypeParameters();
            List<Type> otherTypeParameters = other.getSourceParameters().get( i ).getType().getTypeParameters();

            if ( !thisTypeParameters.equals( otherTypeParameters ) ) {
                return false;
            }
        }

        return isMapNullToDefault() == other.isMapNullToDefault();
    }
}
