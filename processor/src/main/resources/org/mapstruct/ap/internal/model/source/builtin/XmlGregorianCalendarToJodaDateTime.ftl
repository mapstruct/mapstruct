<#-- @ftlvariable name="" type="org.mapstruct.ap.internal.model.VirtualMappingMethod" -->
<#--

     Copyright 2012-2017 Gunnar Morling (http://www.gunnarmorling.de/)
     and/or other contributors as indicated by the @authors tag. See the
     copyright.txt file in the distribution for a full listing of all
     contributors.

     Licensed under the Apache License, Version 2.0 (the "License");
     you may not use this file except in compliance with the License.
     You may obtain a copy of the License at

         http://www.apache.org/licenses/LICENSE-2.0

     Unless required by applicable law or agreed to in writing, software
     distributed under the License is distributed on an "AS IS" BASIS,
     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
     See the License for the specific language governing permissions and
     limitations under the License.

-->
private static <@includeModel object=findType("DateTime")/> ${name}( <@includeModel object=findType("XMLGregorianCalendar")/> xcal ) {
    if ( xcal == null ) {
        return null;
    }

    if ( xcal.getYear() != <@includeModel object=findType("DatatypeConstants")/>.FIELD_UNDEFINED
        && xcal.getMonth() != <@includeModel object=findType("DatatypeConstants")/>.FIELD_UNDEFINED
        && xcal.getDay() != <@includeModel object=findType("DatatypeConstants")/>.FIELD_UNDEFINED
        && xcal.getHour() != <@includeModel object=findType("DatatypeConstants")/>.FIELD_UNDEFINED
        && xcal.getMinute() != <@includeModel object=findType("DatatypeConstants")/>.FIELD_UNDEFINED
        ) {
            if ( xcal.getSecond() != <@includeModel object=findType("DatatypeConstants")/>.FIELD_UNDEFINED
                && xcal.getMillisecond() != <@includeModel object=findType("DatatypeConstants")/>.FIELD_UNDEFINED
                && xcal.getTimezone() != <@includeModel object=findType("DatatypeConstants")/>.FIELD_UNDEFINED ) {
                return new <@includeModel object=findType("DateTime")/>( xcal.getYear(),
                    xcal.getMonth(),
                    xcal.getDay(),
                    xcal.getHour(),
                    xcal.getMinute(),
                    xcal.getSecond(),
                    xcal.getMillisecond(),
                    <@includeModel object=findType("DateTimeZone")/>.forOffsetMillis( xcal.getTimezone() * 60000 )
                );
            }
            else if ( xcal.getSecond() != <@includeModel object=findType("DatatypeConstants")/>.FIELD_UNDEFINED
                && xcal.getMillisecond() != <@includeModel object=findType("DatatypeConstants")/>.FIELD_UNDEFINED ) {
                return new <@includeModel object=findType("DateTime")/>( xcal.getYear(),
                    xcal.getMonth(),
                    xcal.getDay(),
                    xcal.getHour(),
                    xcal.getMinute(),
                    xcal.getSecond(),
                    xcal.getMillisecond()
                );
            }
            else if ( xcal.getSecond() != <@includeModel object=findType("DatatypeConstants")/>.FIELD_UNDEFINED
                && xcal.getTimezone() != <@includeModel object=findType("DatatypeConstants")/>.FIELD_UNDEFINED ) {
                return new <@includeModel object=findType("DateTime")/>( xcal.getYear(),
                    xcal.getMonth(),
                    xcal.getDay(),
                    xcal.getHour(),
                    xcal.getMinute(),
                    xcal.getSecond(),
                    <@includeModel object=findType("DateTimeZone")/>.forOffsetMillis( xcal.getTimezone() * 60000 )
                );
            }
            else if ( xcal.getSecond() != <@includeModel object=findType("DatatypeConstants")/>.FIELD_UNDEFINED ) {
                return new <@includeModel object=findType("DateTime")/>( xcal.getYear(),
                    xcal.getMonth(),
                    xcal.getDay(),
                    xcal.getHour(),
                    xcal.getMinute(),
                    xcal.getSecond()
                );
            }
            else if ( xcal.getTimezone() != <@includeModel object=findType("DatatypeConstants")/>.FIELD_UNDEFINED ) {
                return new <@includeModel object=findType("DateTime")/>( xcal.getYear(),
                    xcal.getMonth(),
                    xcal.getDay(),
                    xcal.getHour(),
                    xcal.getMinute(),
                    <@includeModel object=findType("DateTimeZone")/>.forOffsetMillis( xcal.getTimezone() * 60000 )
            );
            }
            else {
                return new <@includeModel object=findType("DateTime")/>( xcal.getYear(),
                    xcal.getMonth(),
                    xcal.getDay(),
                    xcal.getHour(),
                    xcal.getMinute()
            );
            }
        }
    return null;
}
