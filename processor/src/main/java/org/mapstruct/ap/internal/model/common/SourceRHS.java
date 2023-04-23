/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.model.common;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import org.mapstruct.ap.internal.util.Strings;

import static org.mapstruct.ap.internal.util.Collections.first;

/**
 * SourceRHS Assignment. Right Hand Side (RHS), source part of the assignment.
 *
 * This class contains all information on the source side of an assignment needed for common use in the mapping.
 *
 * @author Sjaak Derksen
 */
public class SourceRHS extends ModelElement implements Assignment {

    private final String sourceReference;
    private final Type sourceType;
    private String sourceLocalVarName;
    private String sourceLoopVarName;
    private final Set<String> existingVariableNames;
    private final String sourceErrorMessagePart;
    private PresenceCheck sourcePresenceCheckerReference;
    private boolean useElementAsSourceTypeForMatching = false;
    private final String sourceParameterName;

    public SourceRHS(String sourceReference, Type sourceType, Set<String> existingVariableNames,
        String sourceErrorMessagePart ) {
        this( sourceReference, sourceReference, null, sourceType, existingVariableNames, sourceErrorMessagePart );
    }

    public SourceRHS(String sourceParameterName, String sourceReference, PresenceCheck sourcePresenceCheckerReference,
        Type sourceType, Set<String> existingVariableNames,  String sourceErrorMessagePart ) {
        this.sourceReference = sourceReference;
        this.sourceType = sourceType;
        this.existingVariableNames = existingVariableNames;
        this.sourceErrorMessagePart = sourceErrorMessagePart;
        this.sourcePresenceCheckerReference = sourcePresenceCheckerReference;
        this.sourceParameterName = sourceParameterName;
    }

    @Override
    public String getSourceReference() {
        return sourceReference;
    }

    @Override
    public boolean isSourceReferenceParameter() {
        return sourceReference.equals( sourceParameterName );
    }

    @Override
    public PresenceCheck getSourcePresenceCheckerReference() {
        return sourcePresenceCheckerReference;
    }

    public void setSourcePresenceCheckerReference(PresenceCheck sourcePresenceCheckerReference) {
        this.sourcePresenceCheckerReference = sourcePresenceCheckerReference;
    }

    @Override
    public Type getSourceType() {
        return sourceType;
    }

    @Override
    public String createUniqueVarName(String desiredName) {
        String result = Strings.getSafeVariableName( desiredName, existingVariableNames );
        existingVariableNames.add( result );
        return result;
    }

    @Override
    public String getSourceLocalVarName() {
        return sourceLocalVarName;
    }

    @Override
    public void setSourceLocalVarName(String sourceLocalVarName) {
        this.sourceLocalVarName = sourceLocalVarName;
    }

    public String getSourceLoopVarName() {
        return sourceLoopVarName;
    }

    public void setSourceLoopVarName(String sourceLoopVarName) {
        this.sourceLoopVarName = sourceLoopVarName;
    }

    @Override
    public Set<Type> getImportTypes() {
        if ( sourcePresenceCheckerReference != null ) {
            return sourcePresenceCheckerReference.getImportTypes();
        }

        return Collections.emptySet();
    }

    @Override
    public List<Type> getThrownTypes() {
        return Collections.emptyList();
    }

    @Override
    public void setAssignment( Assignment assignment ) {
        throw new UnsupportedOperationException( "Not supported." );
    }

    @Override
    public AssignmentType getType() {
        return AssignmentType.DIRECT;
    }

    @Override
    public boolean isCallingUpdateMethod() {
        return false;
    }

    @Override
    public String toString() {
        return sourceReference;
    }

    public String getSourceErrorMessagePart() {
        return sourceErrorMessagePart;
    }

    /**
     * The source type that is to be used when resolving the mapping from source to target.
     *
     * @return the source type to be used in the matching process.
     */
    public Type getSourceTypeForMatching() {
        if ( useElementAsSourceTypeForMatching ) {
            if ( sourceType.isCollectionType() ) {
                return first( sourceType.determineTypeArguments( Collection.class ) );
            }
            else if ( sourceType.isStreamType() ) {
                return first( sourceType.determineTypeArguments( Stream.class ) );
            }
            else if ( sourceType.isArrayType() ) {
                return sourceType.getComponentType();
            }
            else if ( sourceType.isIterableType() ) {
                return first( sourceType.determineTypeArguments( Iterable.class ) );
            }
        }
        return sourceType;
    }

    /**
     * For collection type, use element as source type to find a suitable mapping method.
     *
     * @param useElementAsSourceTypeForMatching uses the element of a collection as source type for the matching process
     */
    public void setUseElementAsSourceTypeForMatching(boolean useElementAsSourceTypeForMatching) {
        this.useElementAsSourceTypeForMatching = useElementAsSourceTypeForMatching;
    }

    @Override
    public String getSourceParameterName() {
        return sourceParameterName;
    }

}
