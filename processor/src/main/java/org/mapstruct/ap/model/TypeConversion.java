/**
 *  Copyright 2012-2014 Gunnar Morling (http://www.gunnarmorling.de/)
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
package org.mapstruct.ap.model;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.mapstruct.ap.model.common.ModelElement;
import org.mapstruct.ap.model.common.Type;

/**
 * An inline conversion between source and target type of a mapping.
 *
 * @author Gunnar Morling
 */
public class TypeConversion extends ModelElement {


    private final Set<Type> importTypes;
    private final List<Type> exceptionTypes;
    private final String sourceReference;
    private final String openExpression;
    private final String closeExpression;
    /**
     * A reference to mapping method in case this is a two-step mapping, e.g. from
     * {@code JAXBElement<Bar>} to {@code Foo} to for which a nested method call will be generated:
     * {@code setFoo(barToFoo( jaxbElemToValue( bar) ) )}
     */
    private MethodReference methodRefChild;

    public TypeConversion( Set<Type> importTypes,
            List<Type> exceptionTypes,
            String openExpression,
            String sourceReference,
            String closeExpression ) {
        this.importTypes = new HashSet<Type>( importTypes );
        this.importTypes.addAll( exceptionTypes );
        this.exceptionTypes = exceptionTypes;
        this.openExpression = openExpression;
        this.sourceReference = sourceReference;
        this.closeExpression = closeExpression;
    }

    @Override
    public Set<Type> getImportTypes() {
        return importTypes;
    }

    public List<Type> getExceptionTypes() {
        return exceptionTypes;
    }

    public String getOpenExpression() {
        return openExpression;
    }

    public String getSourceReference() {
        return sourceReference;
    }

    public String getCloseExpression() {
        return closeExpression;
    }

    public void setMethodRefChild( MethodReference methodRefChild ) {
        this.methodRefChild = methodRefChild;
    }

    public MethodReference getMethodRefChild() {
        return methodRefChild;
    }
}
