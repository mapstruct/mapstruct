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

import org.mapstruct.ap.model.source.BuiltInMethod;
import java.util.HashSet;
import org.mapstruct.ap.model.common.TypeFactory;


/**
 * Registry for all build in methods.
 *
 * @author Sjaak Derksen
 */
public class BuiltInMappingMethods extends HashSet<BuiltInMethod> {

    public BuiltInMappingMethods( TypeFactory typeFactory ) {

        add( new JaxbElemToValue( typeFactory ) );
        add( new ListOfJaxbElemToListOfValue( typeFactory ) );
        add( new DateToXmlGregorianCalendar( typeFactory ) );
        add( new XmlGregorianCalendarToDate( typeFactory ) );
        add( new StringToXmlGregorianCalendar( typeFactory ) );
        add( new XmlGregorianCalendarToString( typeFactory ) );
        add( new CalendarToXmlGregorianCalendar( typeFactory ) );
        add( new XmlGregorianCalendarToCalendar( typeFactory ) );
    }
}
