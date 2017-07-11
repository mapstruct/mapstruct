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
package org.mapstruct.ap.internal.model.assignment;

import java.util.List;
import java.util.Set;

import org.mapstruct.ap.internal.model.common.Assignment;
import org.mapstruct.ap.internal.model.common.ModelElement;
import org.mapstruct.ap.internal.model.common.Type;

/**
 * Base class for decorators (wrappers). Decorator pattern is used to decorate assignments.
 *
 * @author Sjaak Derksen
 */
public abstract class AssignmentWrapper extends ModelElement implements Assignment {

    private final Assignment decoratedAssignment;
    protected final boolean fieldAssignment;

    public AssignmentWrapper( Assignment decoratedAssignment, boolean fieldAssignment ) {
        this.decoratedAssignment = decoratedAssignment;
        this.fieldAssignment = fieldAssignment;
    }

    @Override
    public Set<Type> getImportTypes() {
        return decoratedAssignment.getImportTypes();
    }

    @Override
    public List<Type> getThrownTypes() {
        return decoratedAssignment.getThrownTypes();
    }

    @Override
    public void setAssignment( Assignment assignment ) {
        throw new UnsupportedOperationException("deliberately not implemented");
    }

    public Assignment getAssignment() {
       return decoratedAssignment;
    }

    @Override
    public String getSourceReference() {
        return decoratedAssignment.getSourceReference();
    }

    @Override
    public boolean isSourceReferenceParameter() {
        return decoratedAssignment.isSourceReferenceParameter();
    }

    @Override
    public String getSourcePresenceCheckerReference() {
        return decoratedAssignment.getSourcePresenceCheckerReference();
    }

    @Override
    public Type getSourceType() {
        return decoratedAssignment.getSourceType();
    }

    @Override
    public String getSourceLocalVarName() {
        return decoratedAssignment.getSourceLocalVarName();
    }

    @Override
    public void setSourceLocalVarName(String sourceLocalVarName) {
        decoratedAssignment.setSourceLocalVarName( sourceLocalVarName );
    }

    @Override
    public String getSourceParameterName() {
        return decoratedAssignment.getSourceParameterName();
    }

    @Override
    public AssignmentType getType() {
        return decoratedAssignment.getType();
    }

     @Override
    public boolean isCallingUpdateMethod() {
        return decoratedAssignment.isCallingUpdateMethod();
    }

    @Override
    public String createLocalVarName( String desiredName ) {
        return decoratedAssignment.createLocalVarName( desiredName );
    }

    /**
     *
     * @return {@code true} if the wrapper is for field assignment
     */
    public boolean isFieldAssignment() {
        return fieldAssignment;
    }
}
