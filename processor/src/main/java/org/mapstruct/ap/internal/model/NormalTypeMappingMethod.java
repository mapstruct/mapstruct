/**
 *  Copyright 2012-2017 Gunnar Morling (http://www.gunnarmorling.de/)
 *  and/or other contributors as indicated by the @authors tag. See the
 *  copyright.txt file in the distribution for a full listing of all
 *  contributors.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
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
    private final boolean overridden;
    private final boolean mapNullToDefault;

    NormalTypeMappingMethod(Method method, Collection<String> existingVariableNames, MethodReference factoryMethod,
        boolean mapNullToDefault,
        List<LifecycleCallbackMethodReference> beforeMappingReferences,
        List<LifecycleCallbackMethodReference> afterMappingReferences) {
        super( method, existingVariableNames, beforeMappingReferences, afterMappingReferences );
        this.factoryMethod = factoryMethod;
        this.overridden = method.overridesMethod();
        this.mapNullToDefault = mapNullToDefault;
    }

    @Override
    public Set<Type> getImportTypes() {
        Set<Type> types = super.getImportTypes();
        if ( ( factoryMethod == null ) && ( !isExistingInstanceMapping() ) ) {
            if ( getReturnType().getImplementationType() != null ) {
                types.addAll( getReturnType().getImplementationType().getImportTypes() );
            }
        }
        return types;
    }

    public boolean isMapNullToDefault() {
        return mapNullToDefault;
    }

    public boolean isOverridden() {
        return overridden;
    }

    public MethodReference getFactoryMethod() {
        return this.factoryMethod;
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
