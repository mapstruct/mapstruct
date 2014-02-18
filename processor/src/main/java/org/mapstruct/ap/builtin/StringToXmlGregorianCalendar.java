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

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import org.mapstruct.ap.model.BuiltInMappingMethod;
import static java.util.Arrays.asList;
import java.util.GregorianCalendar;
import java.util.Set;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import org.mapstruct.ap.model.MethodReference;
import org.mapstruct.ap.model.common.ConversionContext;
import org.mapstruct.ap.model.common.Parameter;
import org.mapstruct.ap.model.common.Type;
import org.mapstruct.ap.model.common.TypeFactory;
import static org.mapstruct.ap.util.Collections.asSet;

/**
 *
 * @author Sjaak Derksen
 */
public class StringToXmlGregorianCalendar extends BuiltInMappingMethod {

    private static final Class SOURCE = String.class;
    private static final Class TARGET = XMLGregorianCalendar.class;

    private final TypeFactory typeFactory;
    private ConversionContext conversionContext;

    public StringToXmlGregorianCalendar( TypeFactory typeFactory ) {
        this.typeFactory = typeFactory;
    }

    @Override
    public MethodReference createMethodReference() {
        return new MethodReference(
            getName(),
            asList( new Parameter[] { typeFactory.createParameter( "date" , SOURCE ) } ),
            typeFactory.getType( TARGET ),
            getContextParm()
        );
    }

    @Override
    public Set<Type> getImportTypes() {
         return asSet( new Type[]{ typeFactory.getType( GregorianCalendar.class ),
             typeFactory.getType( SimpleDateFormat.class ),
             typeFactory.getType( DateFormat.class ),
             typeFactory.getType( ParseException.class ),
             typeFactory.getType( DatatypeFactory.class ),
             typeFactory.getType( DatatypeConfigurationException.class ) });
    }

    @Override
    public Type source() {
        return typeFactory.getType( SOURCE ).erasure();
    }

    @Override
    public Type target() {
        return typeFactory.getType( TARGET ).erasure();
    }

    @Override
    public void setConversionContext(ConversionContext conversionContext) {
        this.conversionContext = conversionContext;
    }

    private String getContextParm() {
        return conversionContext.getDateFormat() != null ? "\"" + conversionContext.getDateFormat() + "\"" : "null";
    }
}
