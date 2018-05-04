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
package org.mapstruct.ap.internal.model.common;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

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
    private final Set<String> existingVariableNames;
    private final String sourceErrorMessagePart;
    private final String sourcePresenceCheckerReference;
    private boolean useElementAsSourceTypeForMatching = false;
    private final String sourceParameterName;

    public SourceRHS(String sourceReference, Type sourceType, Set<String> existingVariableNames,
        String sourceErrorMessagePart ) {
        this( sourceReference, sourceReference, null, sourceType, existingVariableNames, sourceErrorMessagePart );
    }

    public SourceRHS(String sourceParameterName, String sourceReference, String sourcePresenceCheckerReference,
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
    public String getSourcePresenceCheckerReference() {
        return sourcePresenceCheckerReference;
    }

    @Override
    public Type getSourceType() {
        return sourceType;
    }

    @Override
    public String createLocalVarName(String desiredName) {
        String result = Strings.getSaveVariableName( desiredName, existingVariableNames );
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

    @Override
    public Set<Type> getImportTypes() {
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
        return useElementAsSourceTypeForMatching && sourceType.isCollectionType() ?
            first( sourceType.determineTypeArguments( Collection.class ) ) : sourceType;
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
