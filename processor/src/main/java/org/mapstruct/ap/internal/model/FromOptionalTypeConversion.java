/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.model;

import java.util.List;
import java.util.Set;

import org.mapstruct.ap.internal.model.assignment.OptionalGetWrapper;
import org.mapstruct.ap.internal.model.common.Assignment;
import org.mapstruct.ap.internal.model.common.ModelElement;
import org.mapstruct.ap.internal.model.common.PresenceCheck;
import org.mapstruct.ap.internal.model.common.Type;

/**
 * An inline conversion from an optional source to it's value.
 *
 * @author Filip Hrisafov
 */
public class FromOptionalTypeConversion extends ModelElement implements Assignment {

    private final Assignment conversionAssignment;
    private final Type optionalType;

    public FromOptionalTypeConversion(Type optionalType, Assignment conversionAssignment) {
        this.conversionAssignment = conversionAssignment;
        this.optionalType = optionalType;
    }

    @Override
    public Set<Type> getImportTypes() {
        return conversionAssignment.getImportTypes();
    }

    @Override
    public List<Type> getThrownTypes() {
        return conversionAssignment.getThrownTypes();
    }

    public Assignment getAssignment() {
        return conversionAssignment;
    }

    @Override
    public String getSourceReference() {
        return conversionAssignment.getSourceReference();
    }

    @Override
    public boolean isSourceReferenceParameter() {
        return conversionAssignment.isSourceReferenceParameter();
    }

    @Override
    public PresenceCheck getSourcePresenceCheckerReference() {
        return conversionAssignment.getSourcePresenceCheckerReference();
    }

    @Override
    public Type getSourceType() {
        return conversionAssignment.getSourceType();
    }

    @Override
    public String createUniqueVarName(String desiredName) {
        return conversionAssignment.createUniqueVarName( desiredName );
    }

    @Override
    public String getSourceLocalVarName() {
        return conversionAssignment.getSourceLocalVarName();
    }

    @Override
    public void setSourceLocalVarName(String sourceLocalVarName) {
        conversionAssignment.setSourceLocalVarName( sourceLocalVarName );
    }

    @Override
    public String getSourceLoopVarName() {
        return conversionAssignment.getSourceLoopVarName();
    }

    @Override
    public void setSourceLoopVarName(String sourceLoopVarName) {
        conversionAssignment.setSourceLoopVarName( sourceLoopVarName );
    }

    @Override
    public String getSourceParameterName() {
        return conversionAssignment.getSourceParameterName();
    }

    @Override
    public void setAssignment(Assignment assignment) {
        this.conversionAssignment.setAssignment( new OptionalGetWrapper( assignment, optionalType ) );
    }

    @Override
    public AssignmentType getType() {
        return conversionAssignment.getType();
    }

    @Override
    public boolean isCallingUpdateMethod() {
        return false;
    }

    @Override
    public String toString() {
        return conversionAssignment.toString();
    }
}
