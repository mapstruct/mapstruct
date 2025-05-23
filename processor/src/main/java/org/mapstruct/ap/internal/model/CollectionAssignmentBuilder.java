/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.model;

import java.util.function.Predicate;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;

import org.mapstruct.ap.internal.gem.CollectionMappingStrategyGem;
import org.mapstruct.ap.internal.gem.NullValueCheckStrategyGem;
import org.mapstruct.ap.internal.gem.NullValuePropertyMappingStrategyGem;
import org.mapstruct.ap.internal.model.assignment.ExistingInstanceSetterWrapperForCollectionsAndMaps;
import org.mapstruct.ap.internal.model.assignment.GetterWrapperForCollectionsAndMaps;
import org.mapstruct.ap.internal.model.assignment.NewInstanceSetterWrapperForCollectionsAndMaps;
import org.mapstruct.ap.internal.model.assignment.SetterWrapperForCollectionsAndMaps;
import org.mapstruct.ap.internal.model.assignment.SetterWrapperForCollectionsAndMapsWithNullCheck;
import org.mapstruct.ap.internal.model.assignment.UpdateWrapper;
import org.mapstruct.ap.internal.model.common.Assignment;
import org.mapstruct.ap.internal.model.common.Assignment.AssignmentType;
import org.mapstruct.ap.internal.model.common.SourceRHS;
import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.model.source.Method;
import org.mapstruct.ap.internal.model.source.SelectionParameters;
import org.mapstruct.ap.internal.util.Message;
import org.mapstruct.ap.internal.util.accessor.Accessor;
import org.mapstruct.ap.internal.util.accessor.AccessorType;

import static org.mapstruct.ap.internal.gem.NullValueCheckStrategyGem.ALWAYS;
import static org.mapstruct.ap.internal.gem.NullValuePropertyMappingStrategyGem.IGNORE;
import static org.mapstruct.ap.internal.gem.NullValuePropertyMappingStrategyGem.SET_TO_DEFAULT;
import static org.mapstruct.ap.internal.gem.NullValuePropertyMappingStrategyGem.SET_TO_NULL;

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
    private AccessorType targetAccessorType;
    private Assignment assignment;
    private SourceRHS sourceRHS;
    private NullValueCheckStrategyGem nvcs;
    private NullValuePropertyMappingStrategyGem nvpms;

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

    public CollectionAssignmentBuilder targetAccessorType(AccessorType targetAccessorType) {
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

    public CollectionAssignmentBuilder nullValueCheckStrategy( NullValueCheckStrategyGem nvcs ) {
        this.nvcs = nvcs;
        return this;
    }

    public CollectionAssignmentBuilder nullValuePropertyMappingStrategy( NullValuePropertyMappingStrategyGem nvpms ) {
        this.nvpms = nvpms;
        return this;
    }

    public Assignment build() {
        Assignment result = assignment;

        CollectionMappingStrategyGem cms = method.getOptions().getMapper().getCollectionMappingStrategy();
        boolean targetImmutable = cms == CollectionMappingStrategyGem.TARGET_IMMUTABLE || targetReadAccessor == null;

        if ( targetAccessorType == AccessorType.SETTER || targetAccessorType.isFieldAssignment() ) {

            if ( result.isCallingUpdateMethod() && !targetImmutable ) {

                // call to an update method
                if ( targetReadAccessor == null ) {
                    ctx.getMessager().printMessage(
                        method.getExecutable(),
                        Message.PROPERTYMAPPING_NO_READ_ACCESSOR_FOR_TARGET_TYPE,
                        targetPropertyName
                    );
                }

                Assignment factoryMethod = ObjectFactoryMethodResolver
                    .getFactoryMethod( method, targetType, SelectionParameters.forSourceRHS( sourceRHS ), ctx );
                result = new UpdateWrapper(
                    result,
                    method.getThrownTypes(),
                    factoryMethod,
                    targetAccessorType.isFieldAssignment(),
                    targetType,
                    true,
                    nvpms == SET_TO_NULL && !targetType.isPrimitive(),
                    nvpms == SET_TO_DEFAULT
                );
            }
            else if ( method.isUpdateMethod() && !targetImmutable ) {

                result = new ExistingInstanceSetterWrapperForCollectionsAndMaps(
                    result,
                    method.getThrownTypes(),
                    targetType,
                    nvcs,
                    nvpms,
                    ctx.getTypeFactory(),
                    targetAccessorType.isFieldAssignment()
                );
            }
            else if ( method.isUpdateMethod() && nvpms == IGNORE ) {

                result = new SetterWrapperForCollectionsAndMapsWithNullCheck(
                    result,
                    method.getThrownTypes(),
                    targetType,
                    ctx.getTypeFactory(),
                    targetAccessorType.isFieldAssignment()
                );
            }
            else if ( setterWrapperNeedsSourceNullCheck( result )
                && canBeMappedOrDirectlyAssigned( result ) ) {

                result = new SetterWrapperForCollectionsAndMapsWithNullCheck(
                    result,
                    method.getThrownTypes(),
                    targetType,
                    ctx.getTypeFactory(),
                    targetAccessorType.isFieldAssignment()
                );
            }
            else if ( canBeMappedOrDirectlyAssigned( result ) ) {
                //TODO init default value

                // target accessor is setter, so wrap the setter in setter map/ collection handling
                result = new SetterWrapperForCollectionsAndMaps(
                    result,
                    method.getThrownTypes(),
                    targetType,
                    targetAccessorType.isFieldAssignment()
                );
            }
            else if ( hasNoArgsConstructor() ) {
                result = new NewInstanceSetterWrapperForCollectionsAndMaps(
                    result,
                    method.getThrownTypes(),
                    targetType,
                    ctx.getTypeFactory(),
                    targetAccessorType.isFieldAssignment() );
            }
            else {
                ctx.getMessager().printMessage(
                    method.getExecutable(),
                    Message.PROPERTYMAPPING_NO_SUITABLE_COLLECTION_OR_MAP_CONSTRUCTOR,
                    targetType
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
                nvpms,
                targetAccessorType.isFieldAssignment()
            );
        }

        return result;
    }

    private boolean canBeMappedOrDirectlyAssigned(Assignment result) {
        return result.getType() != AssignmentType.DIRECT
                  || hasCopyConstructor()
                  || targetType.isEnumSet();
    }

    /**
     * Checks whether the setter wrapper should include a null / presence check or not
     *
     * @param rhs the source right hand side
     * @return whether to include a null / presence check or not
     */
    private boolean setterWrapperNeedsSourceNullCheck(Assignment rhs) {
        if ( rhs.getSourcePresenceCheckerReference() != null ) {
            // If there is a source presence check then we should do a null check
            return true;
        }

        if ( nvcs == ALWAYS ) {
            // NullValueCheckStrategy is ALWAYS -> do a null check
            return true;
        }

        if ( rhs.getType().isDirect() ) {
            return true;
        }

        return false;
    }

    private boolean hasCopyConstructor() {
        return checkConstructorForPredicate( this::hasCopyConstructor );
    }

    private boolean hasNoArgsConstructor() {
        return checkConstructorForPredicate( this::hasNoArgsConstructor );
    }

    private boolean checkConstructorForPredicate(Predicate<Element> predicate) {
        if ( targetType.isCollectionOrMapType() ) {
            if ( "java.util".equals( targetType.getPackageName() ) ) {
                return true;
            }
            else {
                Element sourceElement = targetType.getImplementationType() != null
                                      ? targetType.getImplementationType().getTypeElement()
                                      : targetType.getTypeElement();
                if ( sourceElement != null ) {
                    for ( Element element : sourceElement.getEnclosedElements() ) {
                        if ( element.getKind() == ElementKind.CONSTRUCTOR
                            && element.getModifiers().contains( Modifier.PUBLIC ) ) {
                            if ( predicate.test( element ) ) {
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    private boolean hasNoArgsConstructor(Element element) {
        return ( (ExecutableElement) element ).getParameters().isEmpty();
    }

    private boolean hasCopyConstructor(Element element) {
        if ( element instanceof ExecutableElement ) {
            ExecutableElement ee = (ExecutableElement) element;
            return ee.getParameters().size() == 1
                && ctx.getTypeUtils().isAssignable( targetType.getTypeMirror(), ee.getParameters().get( 0 ).asType() );
        }
        return false;
    }
}
