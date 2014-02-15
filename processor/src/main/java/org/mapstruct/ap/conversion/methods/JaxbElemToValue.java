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
package org.mapstruct.ap.conversion.methods;

import static java.util.Arrays.asList;
import java.util.Set;
import javax.xml.bind.JAXBElement;
import org.mapstruct.ap.model.MethodReference;
import org.mapstruct.ap.model.common.Parameter;
import org.mapstruct.ap.model.common.Type;
import org.mapstruct.ap.model.common.TypeFactory;
import static org.mapstruct.ap.util.Collections.asSet;

/**
 *
 * @author Sjaak Derksen
 */
public class JaxbElemToValue extends BuildInMethod {

    private static final String METHOD_NAME = "convertJAXBElementToValue";
    private static final Class JAXB_TYPE = JAXBElement.class;

    private final TypeFactory typeFactory;

    public JaxbElemToValue( TypeFactory typeFactory ) {
        this.typeFactory = typeFactory;
    }

    @Override
    public MethodReference createMethodReference() {
        return new MethodReference(
            METHOD_NAME,
            asList( new Parameter[] { typeFactory.createParameter( "element", JAXB_TYPE ) } ),
            typeFactory.getType( Object.class )
        );
    }

    @Override
    public String toString() {
        return METHOD_NAME;
    }

    @Override
    public Set<Type> getImportTypes() {
         return asSet( typeFactory.getType( JAXB_TYPE ) );
    }

    @Override
    public boolean doGenericsMatch(Type sourceType, Type targetType) {
        boolean match = false;
        if (sourceType.getTypeParameters().size() == 1) {
            match = sourceType.getTypeParameters().get( 0 ).equals( targetType );
        }
        return match;
    }
}
