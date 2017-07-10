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

import org.mapstruct.ap.internal.model.common.Assignment;
import org.mapstruct.ap.internal.model.common.ParameterBinding;
import org.mapstruct.ap.internal.model.common.SourceRHS;
import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.model.source.ForgedMethod;
import org.mapstruct.ap.internal.model.source.MappingMethodUtils;
import org.mapstruct.ap.internal.model.source.Method;
import org.mapstruct.ap.internal.util.MapperConfiguration;
import org.mapstruct.ap.internal.util.Message;

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
     * Checks if MapStruct is allowed to generate an automatic sub-mapping between {@code sourceType} and @{code
     * targetType}.
     * This will evaluate to {@code true}, when:
     * <li>
     * <ul>Automatic sub-mapping methods generation is not disabled</ul>
     * <ul>MapStruct is allowed to generate an automatic sub-mapping between the {@code sourceType} and {@code
     * targetType}</ul>
     * </li>
     *
     * @param sourceType candidate source type to generate a sub-mapping from
     * @param targetType candidate target type to generate a sub-mapping for
     *
     * @return {@code true} if MapStruct can try to generate an automatic sub-mapping between the types.
     */
    boolean canGenerateAutoSubMappingBetween(Type sourceType, Type targetType) {
        return !isDisableSubMappingMethodsGeneration() &&
            ctx.canGenerateAutoSubMappingBetween( sourceType, targetType );
    }

    private boolean isDisableSubMappingMethodsGeneration() {
        MapperConfiguration configuration = MapperConfiguration.getInstanceOn( ctx.getMapperTypeElement() );
        return configuration.isDisableSubMappingMethodsGeneration();
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

        if ( ctx.getForgedMethodsUnderCreation().containsKey( forgedMethod ) ) {
            return createAssignment( sourceRHS, ctx.getForgedMethodsUnderCreation().get( forgedMethod ) );
        }
        else {
            ctx.getForgedMethodsUnderCreation().put( forgedMethod, forgedMethod );
        }

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

        Assignment forgedAssignment = createForgedAssignment( sourceRHS, forgedMethod, forgedMappingMethod );
        ctx.getForgedMethodsUnderCreation().remove( forgedMethod );
        return forgedAssignment;
    }

    Assignment createForgedAssignment(SourceRHS source, ForgedMethod methodRef, MappingMethod mappingMethod) {
        if ( mappingMethod == null ) {
            return null;
        }
        if (methodRef.getMappingOptions().isRestrictToDefinedMappings() ||
            !ctx.getMappingsToGenerate().contains( mappingMethod )) {
            // If the mapping options are restricted only to the defined mappings, then use the mapping method.
            // See https://github.com/mapstruct/mapstruct/issues/1148
            ctx.getMappingsToGenerate().add( mappingMethod );
        }
        else {
            String existingName = ctx.getExistingMappingMethod( mappingMethod ).getName();
            methodRef = new ForgedMethod( existingName, methodRef );
        }

        return createAssignment( source, methodRef );
    }

    private Assignment createAssignment(SourceRHS source, ForgedMethod methodRef) {
        Assignment assignment = MethodReference.forForgedMethod(
            methodRef,
            ParameterBinding.fromParameters( methodRef.getParameters() )
        );
        assignment.setAssignment( source );

        return assignment;
    }

    /**
     * Reports that a mapping could not be created.
     *
     * @param method the method that should be mapped
     * @param sourceErrorMessagePart the error message part for the source
     * @param sourceType the source type of the mapping
     * @param targetType the type of the target mapping
     * @param targetPropertyName the name of the target property
     */
    void reportCannotCreateMapping(Method method, String sourceErrorMessagePart, Type sourceType, Type targetType,
        String targetPropertyName) {
        ctx.getMessager().printMessage(
            method.getExecutable(),
            Message.PROPERTYMAPPING_MAPPING_NOT_FOUND,
            sourceErrorMessagePart,
            targetType,
            targetPropertyName,
            targetType,
            sourceType
        );
    }
}
