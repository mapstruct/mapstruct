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
package org.mapstruct.ap.processor.creation;

import java.util.List;
import javax.lang.model.type.TypeMirror;
import javax.tools.Diagnostic;
import org.mapstruct.ap.model.Assignment;
import org.mapstruct.ap.model.FactoryMethod;
import org.mapstruct.ap.model.IterableMappingMethod;
import org.mapstruct.ap.model.assignment.SetterWrapper;
import org.mapstruct.ap.model.common.Type;
import org.mapstruct.ap.model.source.Method;
import org.mapstruct.ap.util.Strings;

/**
 * This class is responsible for building iterable mapping methods.
 *
 *
 * @author Sjaak Derksen
 */
public class IterableMappingBuilder extends MappingBuilder {

    public IterableMappingBuilder( MappingContext ctx ) {
        super( ctx );
    }


    public IterableMappingMethod build( Method method, String dateFormat, List<TypeMirror> qualifiers ) {

        Type sourceElementType = method.getSourceParameters().iterator().next().getType().getTypeParameters().get( 0 );
        Type targetElementType = method.getResultType().getTypeParameters().get( 0 );
        String conversionStr = Strings.getSaveVariableName( sourceElementType.getName(), method.getParameterNames() );

        Assignment assignment = getMappingResolver().getTargetAssignment( method,
            "collection element",
            sourceElementType,
            targetElementType,
            null, // there is no targetPropertyName
            dateFormat,
            qualifiers,
            conversionStr
        );

        if ( assignment == null ) {
            String message = String.format(
                    "Can't create implementation of method %s. Found no method nor built-in conversion for mapping "
                    + "source element type into target element type.",
                    method
            );
            method.printMessage( getMessager(), Diagnostic.Kind.ERROR, message );
        }

        // target accessor is setter, so decorate assignment as setter
        assignment = new SetterWrapper( assignment, method.getThrownTypes() );

        FactoryMethod factoryMethod = getFactoryMethod( method.getReturnType() );
        return new IterableMappingMethod( method, assignment, factoryMethod );
    }
}
