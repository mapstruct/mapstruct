/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.model;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.mapstruct.ap.internal.model.common.Assignment;
import org.mapstruct.ap.internal.model.common.ModelElement;
import org.mapstruct.ap.internal.model.common.PresenceCheck;
import org.mapstruct.ap.internal.model.common.Type;

/**
 * An inline conversion between source and target type of a mapping.
 *
 * @author Gunnar Morling
 */
public class TypeConversion extends ModelElement implements Assignment {

    private static final String SOURCE_REFERENCE_PATTERN = "<SOURCE>";

    private final Set<Type> importTypes;
    private final List<Type> thrownTypes;
    private final String openExpression;
    private final String closeExpression;

    /**
     * A reference to mapping method in case this is a two-step mapping, e.g. from
     * {@code JAXBElement<Bar>} to {@code Foo} to for which a nested method call will be generated:
     * {@code setFoo(barToFoo( jaxbElemToValue( bar) ) )}
     */
    private Assignment assignment;

    public TypeConversion( Set<Type> importTypes,
                           List<Type> exceptionTypes,
                           String expression ) {
        this.importTypes = new HashSet<>( importTypes );
        this.importTypes.addAll( exceptionTypes );
        this.thrownTypes = exceptionTypes;

        int patternIndex = expression.indexOf( SOURCE_REFERENCE_PATTERN );
        this.openExpression = expression.substring( 0, patternIndex );
        this.closeExpression = expression.substring( patternIndex + 8 );
    }

    @Override
    public Set<Type> getImportTypes() {
        return importTypes;
    }

    @Override
    public List<Type> getThrownTypes() {
        return thrownTypes;
    }

    public String getOpenExpression() {
        return openExpression;
    }

    public String getCloseExpression() {
        return closeExpression;
    }

    public Assignment getAssignment() {
        return assignment;
    }

    @Override
    public String getSourceReference() {
        return assignment.getSourceReference();
    }

    @Override
    public boolean isSourceReferenceParameter() {
        return assignment.isSourceReferenceParameter();
    }

    @Override
    public PresenceCheck getSourcePresenceCheckerReference() {
        return assignment.getSourcePresenceCheckerReference();
    }

    @Override
    public Type getSourceType() {
        return assignment.getSourceType();
    }

    @Override
    public String createUniqueVarName(String desiredName ) {
        return assignment.createUniqueVarName( desiredName );
    }

    @Override
    public String getSourceLocalVarName() {
        return assignment.getSourceLocalVarName();
    }

    @Override
    public void setSourceLocalVarName(String sourceLocalVarName) {
        assignment.setSourceLocalVarName( sourceLocalVarName );
    }

    @Override
    public String getSourceLoopVarName() {
        return assignment.getSourceLoopVarName();
    }

    @Override
    public void setSourceLoopVarName(String sourceLoopVarName) {
        assignment.setSourceLoopVarName( sourceLoopVarName );
    }

    @Override
    public String getSourceParameterName() {
        return assignment.getSourceParameterName();
    }

    @Override
    public void setAssignment( Assignment assignment ) {
        this.assignment = assignment;
    }

    @Override
    public Assignment.AssignmentType getType() {

        switch ( assignment.getType() ) {
            case DIRECT:
                return Assignment.AssignmentType.TYPE_CONVERTED;
            case MAPPED:
                return Assignment.AssignmentType.MAPPED_TYPE_CONVERTED;
            default:
                return null;
        }
    }

    @Override
    public boolean isCallingUpdateMethod() {
        return false;
    }

    @Override
    public String toString() {
        String argument = getAssignment() != null ? getAssignment().toString() : getSourceReference();
        return openExpression + argument + closeExpression;
    }
}
