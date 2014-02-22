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
package org.mapstruct.ap.model.source.builtin;

import java.util.List;
import javax.xml.bind.JAXBElement;

import org.mapstruct.ap.model.common.Parameter;
import org.mapstruct.ap.model.common.Type;
import org.mapstruct.ap.model.common.TypeFactory;

/**
 * @author Sjaak Derksen
 */
public class ListOfJaxbElemToListOfValue extends BuiltInMethod {

    private final Parameter parameter;
    private final Type returnType;
    private final Type genericParam;

    public ListOfJaxbElemToListOfValue(TypeFactory typeFactory) {
        this.parameter = new Parameter( "elementList", typeFactory.getType( List.class ) );
        this.returnType = typeFactory.getType( List.class );
        this.genericParam = typeFactory.getType( JAXBElement.class ).erasure();
    }

    @Override
    public boolean doTypeVarsMatch(Type sourceType, Type targetType) {
        boolean match = false;
        if ( ( sourceType.getTypeParameters().size() == 1 ) && ( targetType.getTypeParameters().size() == 1 ) ) {
            Type typeParam = sourceType.getTypeParameters().get( 0 );
            if ( typeParam.erasure().equals( genericParam ) && ( typeParam.getTypeParameters().size() == 1 ) ) {
                match = typeParam.getTypeParameters().get( 0 ).equals( targetType.getTypeParameters().get( 0 ) );
            }
        }
        return match;
    }

    @Override
    public Parameter getParameter() {
        return parameter;
    }

    @Override
    public Type getReturnType() {
        return returnType;
    }
}
