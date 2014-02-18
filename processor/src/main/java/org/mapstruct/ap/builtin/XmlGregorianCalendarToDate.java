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
package org.mapstruct.ap.builtin;

import org.mapstruct.ap.model.BuiltInMappingMethod;
import static java.util.Arrays.asList;
import java.util.Date;
import javax.xml.datatype.XMLGregorianCalendar;
import org.mapstruct.ap.model.MethodReference;
import org.mapstruct.ap.model.common.Parameter;
import org.mapstruct.ap.model.common.Type;
import org.mapstruct.ap.model.common.TypeFactory;

/**
 *
 * @author Sjaak Derksen
 */
public class XmlGregorianCalendarToDate extends BuiltInMappingMethod {

    private static final Class SOURCE = XMLGregorianCalendar.class;
    private static final Class TARGET = Date.class;

    private final TypeFactory typeFactory;

    public XmlGregorianCalendarToDate( TypeFactory typeFactory ) {
        this.typeFactory = typeFactory;
    }

    @Override
    public MethodReference createMethodReference() {
        return new MethodReference(
            getName(),
            asList( new Parameter[] { typeFactory.createParameter( "xcal", SOURCE ) } ),
            typeFactory.getType( TARGET ),
            null
        );
    }

    @Override
    public Type source() {
        return typeFactory.getType( SOURCE ).erasure();
    }

    @Override
    public Type target() {
        return typeFactory.getType( TARGET ).erasure();
    }
}
