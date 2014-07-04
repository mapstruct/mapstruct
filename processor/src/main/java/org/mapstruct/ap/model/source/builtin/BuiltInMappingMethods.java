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

import java.util.Arrays;
import java.util.List;

import org.mapstruct.ap.model.common.TypeFactory;

/**
 * Registry for all built-in methods.
 *
 * @author Sjaak Derksen
 */
public class BuiltInMappingMethods {

    private final List<BuiltInMethod> builtInMethods;

    public BuiltInMappingMethods(TypeFactory typeFactory) {
        builtInMethods = Arrays.asList(
            new JaxbElemToValue( typeFactory ),
            new ListOfJaxbElemToListOfValue( typeFactory ),
            new DateToXmlGregorianCalendar( typeFactory ),
            new XmlGregorianCalendarToDate( typeFactory ),
            new StringToXmlGregorianCalendar( typeFactory ),
            new XmlGregorianCalendarToString( typeFactory ),
            new CalendarToXmlGregorianCalendar( typeFactory ),
            new XmlGregorianCalendarToCalendar( typeFactory ),
            new CalendarToDate( typeFactory ),
            new DateToCalendar( typeFactory ),
            new CalendarToString( typeFactory ),
            new StringToCalendar( typeFactory )

        );
    }

    public List<BuiltInMethod> getBuiltInMethods() {
        return builtInMethods;
    }
}
