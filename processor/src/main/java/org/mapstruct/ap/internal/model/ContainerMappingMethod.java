/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.model;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.mapstruct.ap.internal.model.common.Assignment;
import org.mapstruct.ap.internal.model.common.Parameter;
import org.mapstruct.ap.internal.model.common.PresenceCheck;
import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.model.presence.NullPresenceCheck;
import org.mapstruct.ap.internal.model.source.Method;
import org.mapstruct.ap.internal.model.source.SelectionParameters;
import org.mapstruct.ap.internal.util.Strings;

/**
 * A {@link MappingMethod} implemented by a {@link Mapper} class which does mapping of generic types.
 * For example Iterable or Stream.
 * The generic elements are mapped either by a {@link TypeConversion} or another mapping method.
 *
 * @author Filip Hrisafov
 */
public abstract class ContainerMappingMethod extends NormalTypeMappingMethod {
    private final Assignment elementAssignment;
    private final String loopVariableName;
    private final SelectionParameters selectionParameters;
    private final String index1Name;
    private final String index2Name;
    private final Parameter sourceParameter;
    private final PresenceCheck sourceParameterPresenceCheck;
    private IterableCreation iterableCreation;

    ContainerMappingMethod(Method method, List<Annotation> annotations,
                           Collection<String> existingVariables, Assignment parameterAssignment,
        MethodReference factoryMethod, boolean mapNullToDefault, String loopVariableName,
        List<LifecycleCallbackMethodReference> beforeMappingReferences,
        List<LifecycleCallbackMethodReference> afterMappingReferences,
        SelectionParameters selectionParameters) {
        super( method, annotations, existingVariables, factoryMethod, mapNullToDefault, beforeMappingReferences,
            afterMappingReferences );
        this.elementAssignment = parameterAssignment;
        this.loopVariableName = loopVariableName;
        this.selectionParameters = selectionParameters != null ? selectionParameters : SelectionParameters.empty();

        this.index1Name = Strings.getSafeVariableName( "i", existingVariables );
        this.index2Name = Strings.getSafeVariableName( "j", existingVariables );

        Parameter sourceParameter = null;
        for ( Parameter parameter : getParameters() ) {
            if ( !parameter.isMappingTarget() && !parameter.isMappingContext() ) {
                sourceParameter = parameter;
                break;
            }
        }

        if ( sourceParameter == null ) {
            throw new IllegalStateException( "Method " + this + " has no source parameter." );
        }

        this.sourceParameter = sourceParameter;
        this.sourceParameterPresenceCheck = new NullPresenceCheck( this.sourceParameter.getName() );

    }

    public Parameter getSourceParameter() {
        return sourceParameter;
    }

    public PresenceCheck getSourceParameterPresenceCheck() {
        return sourceParameterPresenceCheck;
    }

    public IterableCreation getIterableCreation() {
        if ( iterableCreation == null ) {
            iterableCreation = IterableCreation.create( this, getSourceParameter() );
        }
        return iterableCreation;
    }

    public Assignment getElementAssignment() {
        return elementAssignment;
    }

    @Override
    public Set<Type> getImportTypes() {
        Set<Type> types = super.getImportTypes();
        if ( elementAssignment != null ) {
            types.addAll( elementAssignment.getImportTypes() );
        }

        if ( iterableCreation != null ) {
            types.addAll( iterableCreation.getImportTypes() );
        }
        return types;
    }

    public String getLoopVariableName() {
        return loopVariableName;
    }

    public abstract Type getResultElementType();

    public String getIndex1Name() {
        return index1Name;
    }

    public String getIndex2Name() {
        return index2Name;
    }

    @Override
    public int hashCode() {
        //Needed for Checkstyle, otherwise it fails due to EqualsHashCode rule
        return super.hashCode();
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

        if ( !super.equals( obj ) ) {
            return false;
        }

        ContainerMappingMethod other = (ContainerMappingMethod) obj;

        if ( !Objects.equals( selectionParameters, other.selectionParameters ) ) {
            return false;
        }

        return true;
    }

}
