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

import org.mapstruct.ap.internal.model.assignment.ExistingInstanceSetterWrapperForCollectionsAndMaps;
import org.mapstruct.ap.internal.model.assignment.GetterWrapperForCollectionsAndMaps;
import org.mapstruct.ap.internal.model.assignment.SetterWrapperForCollectionsAndMaps;
import org.mapstruct.ap.internal.model.assignment.SetterWrapperForCollectionsAndMapsWithNullCheck;
import org.mapstruct.ap.internal.model.assignment.UpdateWrapper;
import org.mapstruct.ap.internal.model.common.Assignment;
import org.mapstruct.ap.internal.model.common.SourceRHS;
import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.model.source.Method;
import org.mapstruct.ap.internal.model.source.SelectionParameters;
import org.mapstruct.ap.internal.prism.CollectionMappingStrategyPrism;
import org.mapstruct.ap.internal.prism.NullValueCheckStrategyPrism;
import org.mapstruct.ap.internal.prism.NullValueMappingStrategyPrism;
import org.mapstruct.ap.internal.util.Message;
import org.mapstruct.ap.internal.util.accessor.Accessor;

/**
 * A builder that is used for creating an assignment to a collection.
 *
 * The created assignments to the following null checks:
 * <ul>
 *     <li>source-null-check - For this the {@link SetterWrapperForCollectionsAndMapsWithNullCheck} is used when a
 *     direct assignment is done or the {@link org.mapstruct.NullValueCheckStrategy} is
 *     {@link org.mapstruct.NullValueCheckStrategy#ALWAYS}. It is also done in
 *     {@link ExistingInstanceSetterWrapperForCollectionsAndMaps} which extends
 *     {@link SetterWrapperForCollectionsAndMapsWithNullCheck}</li>
 *     <li>target-null-check - Done in the {@link ExistingInstanceSetterWrapperForCollectionsAndMaps}</li>
 *     <li>local-var-null-check - Done in {@link ExistingInstanceSetterWrapperForCollectionsAndMaps}, and
 *     {@link SetterWrapperForCollectionsAndMapsWithNullCheck}</li>
 * </ul>
 *
 * A local-var-null-check is needed in the following cases:
 *
 * <ul>
 *     <li>Presence check with direct assignment - We need a null check before setting, because we use the copy
 *     constructor</li>
 *     <li>Presence check for existing instance mapping - We need the null check because we call addAll / putAll.</li>
 *     <li>No Presence check and direct assignment - We use the copy constructor</li>
 *     <li>No Presence check and {@link org.mapstruct.NullValueCheckStrategy#ALWAYS} - the user requested one</li>
 * </ul>
 *
 * @author Filip Hrisafov
 */
public class CollectionAssignmentBuilder {
    private MappingBuilderContext ctx;
    private Method method;
    private Accessor targetReadAccessor;
    private Type targetType;
    private String targetPropertyName;
    private PropertyMapping.TargetWriteAccessorType targetAccessorType;
    private Assignment assignment;
    private SourceRHS sourceRHS;

    public CollectionAssignmentBuilder mappingBuilderContext(MappingBuilderContext ctx) {
        this.ctx = ctx;
        return this;
    }

    public CollectionAssignmentBuilder method(Method method) {
        this.method = method;
        return this;
    }

    public CollectionAssignmentBuilder targetReadAccessor(Accessor targetReadAccessor) {
        this.targetReadAccessor = targetReadAccessor;
        return this;
    }

    public CollectionAssignmentBuilder targetType(Type targetType) {
        this.targetType = targetType;
        return this;
    }

    public CollectionAssignmentBuilder targetPropertyName(String targetPropertyName) {
        this.targetPropertyName = targetPropertyName;
        return this;
    }

    public CollectionAssignmentBuilder targetAccessorType(PropertyMapping.TargetWriteAccessorType targetAccessorType) {
        this.targetAccessorType = targetAccessorType;
        return this;
    }

    /**
     * @param assignment the assignment that needs to be invoked
     *
     * @return this builder for chaining
     */
    public CollectionAssignmentBuilder assignment(Assignment assignment) {
        this.assignment = assignment;
        return this;
    }

    /**
     * @param sourceRHS the source right hand side for getting the property for mapping
     *
     * @return this builder for chaining
     */
    public CollectionAssignmentBuilder rightHandSide(SourceRHS sourceRHS) {
        this.sourceRHS = sourceRHS;
        return this;
    }

    public Assignment build() {
        Assignment result = assignment;

        CollectionMappingStrategyPrism cms = method.getMapperConfiguration().getCollectionMappingStrategy();
        boolean targetImmutable = cms == CollectionMappingStrategyPrism.TARGET_IMMUTABLE || targetReadAccessor == null;

        if ( targetAccessorType == PropertyMapping.TargetWriteAccessorType.SETTER ||
            targetAccessorType == PropertyMapping.TargetWriteAccessorType.FIELD ) {

            if ( result.isCallingUpdateMethod() && !targetImmutable ) {

                // call to an update method
                if ( targetReadAccessor == null ) {
                    ctx.getMessager().printMessage(
                        method.getExecutable(),
                        Message.PROPERTYMAPPING_NO_READ_ACCESSOR_FOR_TARGET_TYPE,
                        targetPropertyName
                    );
                }

                Assignment factoryMethod = ctx.getMappingResolver()
                    .getFactoryMethod( method, targetType, SelectionParameters.forSourceRHS( sourceRHS ) );
                result = new UpdateWrapper(
                    result,
                    method.getThrownTypes(),
                    factoryMethod,
                    PropertyMapping.TargetWriteAccessorType.isFieldAssignment( targetAccessorType ),
                    targetType,
                    true,
                    mapNullToDefault()
                );
            }
            else if ( method.isUpdateMethod() && !targetImmutable ) {

                result = new ExistingInstanceSetterWrapperForCollectionsAndMaps(
                    result,
                    method.getThrownTypes(),
                    targetType,
                    method.getMapperConfiguration().getNullValueCheckStrategy(),
                    ctx.getTypeFactory(),
                    PropertyMapping.TargetWriteAccessorType.isFieldAssignment( targetAccessorType ),
                    mapNullToDefault()
                );
            }
            else if ( result.getType() == Assignment.AssignmentType.DIRECT ||
                method.getMapperConfiguration().getNullValueCheckStrategy() == NullValueCheckStrategyPrism.ALWAYS ) {

                result = new SetterWrapperForCollectionsAndMapsWithNullCheck(
                    result,
                    method.getThrownTypes(),
                    targetType,
                    ctx.getTypeFactory(),
                    PropertyMapping.TargetWriteAccessorType.isFieldAssignment( targetAccessorType ),
                    mapNullToDefault()
                );
            }
            else {
                // target accessor is setter, so wrap the setter in setter map/ collection handling
                result = new SetterWrapperForCollectionsAndMaps(
                    result,
                    method.getThrownTypes(),
                    targetType,
                    PropertyMapping.TargetWriteAccessorType.isFieldAssignment( targetAccessorType )
                );
            }
        }
        else {
            if ( targetImmutable ) {
                ctx.getMessager().printMessage(
                    method.getExecutable(),
                    Message.PROPERTYMAPPING_NO_WRITE_ACCESSOR_FOR_TARGET_TYPE,
                    targetPropertyName
                );
            }

            // target accessor is getter, so wrap the setter in getter map/ collection handling
            result = new GetterWrapperForCollectionsAndMaps(
                result,
                method.getThrownTypes(),
                targetType,
                PropertyMapping.TargetWriteAccessorType.isFieldAssignment( targetAccessorType )
            );
        }

        return result;
    }

    private boolean mapNullToDefault() {
        return method.getMapperConfiguration().getNullValueMappingStrategy()
            == NullValueMappingStrategyPrism.RETURN_DEFAULT;
    }
}
