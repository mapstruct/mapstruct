/**
 *  Copyright 2012-2015 Gunnar Morling (http://www.gunnarmorling.de/)
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

import org.mapstruct.ap.internal.model.assignment.Assignment;
import org.mapstruct.ap.internal.model.common.ModelElement;
import org.mapstruct.ap.internal.model.common.Type;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    TypeConversion( Set<Type> importTypes,
            List<Type> exceptionTypes,
            String expression ) {
        this.importTypes = new HashSet<Type>( importTypes );
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
    public boolean isUpdateMethod() {
       return false;
    }

}
