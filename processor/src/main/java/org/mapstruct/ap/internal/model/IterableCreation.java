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

import java.util.HashSet;
import java.util.Set;

import org.mapstruct.ap.internal.model.common.ModelElement;
import org.mapstruct.ap.internal.model.common.Parameter;
import org.mapstruct.ap.internal.model.common.Type;

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
    private final boolean canUseSize;
    private final boolean loadFactorAdjustment;

    private IterableCreation(Type resultType, Parameter sourceParameter, MethodReference factoryMethod) {
        this.resultType = resultType;
        this.sourceParameter = sourceParameter;
        this.factoryMethod = factoryMethod;
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

    public boolean isCanUseSize() {
        return canUseSize;
    }

    public boolean isLoadFactorAdjustment() {
        return loadFactorAdjustment;
    }

    @Override
    public Set<Type> getImportTypes() {
        Set<Type> types = new HashSet<Type>();
        if ( factoryMethod == null && resultType.getImplementationType() != null ) {
            types.addAll( resultType.getImplementationType().getImportTypes() );
        }
        return types;
    }
}
