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
import org.mapstruct.ap.model.MapMappingMethod;
import org.mapstruct.ap.model.assignment.LocalVarWrapper;
import org.mapstruct.ap.model.common.Type;
import org.mapstruct.ap.model.source.Method;

/**
 * This class is responsible for building map mapping methods.
 *
 * @author Sjaak Derksen
 */
public class MapMappingBuilder extends MappingBuilder {

    public MapMappingBuilder( MappingContext ctx ) {
        super( ctx );
    }


    public MapMappingMethod build( Method method,
            String keyDateFormat,
            String valueDateFormat,
            List<TypeMirror> keyQualifiers,
            List<TypeMirror> valueQualifiers ) {

        List<Type> sourceTypeParams = method.getSourceParameters().iterator().next().getType().getTypeParameters();
        List<Type> resultTypeParams = method.getResultType().getTypeParameters();

        // find mapping method or conversion for key
        Type keySourceType = sourceTypeParams.get( 0 );
        Type keyTargetType = resultTypeParams.get( 0 );

        Assignment keyAssignment = getMappingResolver().getTargetAssignment( method,
            "map key",
            keySourceType,
            keyTargetType,
            null, // there is no targetPropertyName
            keyDateFormat,
            keyQualifiers,
            "entry.getKey()"
        );

        if ( keyAssignment == null ) {
            String message = String.format( "Can't create implementation of method %s. Found no method nor built-in "
                    + "conversion for mapping source key type to target key type.", method );
            method.printMessage( getMessager(), Diagnostic.Kind.ERROR, message );
        }

        // find mapping method or conversion for value
        Type valueSourceType = sourceTypeParams.get( 1 );
        Type valueTargetType = resultTypeParams.get( 1 );

        Assignment valueAssignment = getMappingResolver().getTargetAssignment( method,
            "map value",
            valueSourceType,
            valueTargetType,
            null, // there is no targetPropertyName
            valueDateFormat,
            valueQualifiers,
            "entry.getValue()"
        );

        if ( valueAssignment == null ) {
            String message = String.format( "Can't create implementation of method %s. Found no method nor built-in "
                    + "conversion for mapping source value type to target value type.", method );
            method.printMessage( getMessager(), Diagnostic.Kind.ERROR, message );
        }

        FactoryMethod factoryMethod = getFactoryMethod( method.getReturnType() );

        keyAssignment = new LocalVarWrapper( keyAssignment, method.getThrownTypes() );
        valueAssignment = new LocalVarWrapper( valueAssignment, method.getThrownTypes() );

        return new MapMappingMethod( method, keyAssignment, valueAssignment, factoryMethod );
    }

}
