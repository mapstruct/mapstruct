/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.model.assignment;

import java.util.List;
import java.util.Set;

import org.mapstruct.ap.internal.model.common.Assignment;
import org.mapstruct.ap.internal.model.common.ModelElement;
import org.mapstruct.ap.internal.model.common.PresenceCheck;
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
    public PresenceCheck getSourcePresenceCheckerReference() {
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
    public String getSourceLoopVarName() {
        return decoratedAssignment.getSourceLoopVarName();
    }

    @Override
    public void setSourceLoopVarName(String sourceLoopVarName) {
        decoratedAssignment.setSourceLoopVarName( sourceLoopVarName );
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
    public String createUniqueVarName(String desiredName ) {
        return decoratedAssignment.createUniqueVarName( desiredName );
    }

    /**
     *
     * @return {@code true} if the wrapper is for field assignment
     */
    public boolean isFieldAssignment() {
        return fieldAssignment;
    }

}
