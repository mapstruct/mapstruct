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

import org.mapstruct.ap.internal.model.assignment.Assignment;
import org.mapstruct.ap.internal.model.common.ParameterBinding;
import org.mapstruct.ap.internal.model.source.ForgedMethod;
import org.mapstruct.ap.internal.model.source.MappingMethodUtils;
import org.mapstruct.ap.internal.model.source.Method;

/**
 * @author Filip Hrisafov
 */
class AbstractBaseBuilder<B extends AbstractBaseBuilder<B>> {

    protected B myself;
    protected MappingBuilderContext ctx;
    protected Method method;

    AbstractBaseBuilder(Class<B> selfType) {
        myself = selfType.cast( this );
    }

    public B mappingContext(MappingBuilderContext mappingContext) {
        this.ctx = mappingContext;
        return myself;
    }

    public B method(Method sourceMethod) {
        this.method = sourceMethod;
        return myself;
    }

    /**
     * Creates a forged assignment from the provided {@code sourceRHS} and {@code forgedMethod}. If a mapping method
     * for the {@code forgedMethod} already exists, then this method used for the assignment.
     *
     * @param sourceRHS that needs to be used for the assignment
     * @param forgedMethod the forged method for which we want to create an {@link Assignment}
     *
     * @return See above
     */
    Assignment createForgedAssignment(SourceRHS sourceRHS, ForgedMethod forgedMethod) {
        MappingMethod forgedMappingMethod;
        if ( MappingMethodUtils.isEnumMapping( forgedMethod ) ) {
            forgedMappingMethod = new ValueMappingMethod.Builder()
                .method( forgedMethod )
                .valueMappings( forgedMethod.getMappingOptions().getValueMappings() )
                .mappingContext( ctx )
                .build();
        }
        else {
            forgedMappingMethod = new BeanMappingMethod.Builder()
                .forgedMethod( forgedMethod )
                .mappingContext( ctx )
                .build();
        }

        return createForgedAssignment( sourceRHS, forgedMethod, forgedMappingMethod );
    }

    Assignment createForgedAssignment(SourceRHS source, ForgedMethod methodRef, MappingMethod mappingMethod) {
        if ( mappingMethod == null ) {
            return null;
        }
        if ( !ctx.getMappingsToGenerate().contains( mappingMethod ) ) {
            ctx.getMappingsToGenerate().add( mappingMethod );
        }
        else {
            String existingName = ctx.getExistingMappingMethod( mappingMethod ).getName();
            methodRef = new ForgedMethod( existingName, methodRef );
        }

        Assignment assignment = MethodReference.forForgedMethod(
            methodRef,
            ParameterBinding.fromParameters( methodRef.getParameters() ) );
        assignment.setAssignment( source );

        return assignment;
    }
}
