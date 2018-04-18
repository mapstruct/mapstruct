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
private static org.joda.time.DateTime ${name}( javax.xml.datatype.XMLGregorianCalendar xcal ) {
    if ( xcal == null ) {
        return null;
    }

    if ( xcal.getYear() != javax.xml.datatype.DatatypeConstants.FIELD_UNDEFINED
        && xcal.getMonth() != javax.xml.datatype.DatatypeConstants.FIELD_UNDEFINED
        && xcal.getDay() != javax.xml.datatype.DatatypeConstants.FIELD_UNDEFINED
        && xcal.getHour() != javax.xml.datatype.DatatypeConstants.FIELD_UNDEFINED
        && xcal.getMinute() != javax.xml.datatype.DatatypeConstants.FIELD_UNDEFINED
        ) {
            if ( xcal.getSecond() != javax.xml.datatype.DatatypeConstants.FIELD_UNDEFINED
                && xcal.getMillisecond() != javax.xml.datatype.DatatypeConstants.FIELD_UNDEFINED
                && xcal.getTimezone() != javax.xml.datatype.DatatypeConstants.FIELD_UNDEFINED ) {
                return new org.joda.time.DateTime( xcal.getYear(),
                    xcal.getMonth(),
                    xcal.getDay(),
                    xcal.getHour(),
                    xcal.getMinute(),
                    xcal.getSecond(),
                    xcal.getMillisecond(),
                    org.joda.time.DateTimeZone.forOffsetMillis( xcal.getTimezone() * 60000 )
                );
            }
            else if ( xcal.getSecond() != javax.xml.datatype.DatatypeConstants.FIELD_UNDEFINED
                && xcal.getMillisecond() != javax.xml.datatype.DatatypeConstants.FIELD_UNDEFINED ) {
                return new org.joda.time.DateTime( xcal.getYear(),
                    xcal.getMonth(),
                    xcal.getDay(),
                    xcal.getHour(),
                    xcal.getMinute(),
                    xcal.getSecond(),
                    xcal.getMillisecond()
                );
            }
            else if ( xcal.getSecond() != javax.xml.datatype.DatatypeConstants.FIELD_UNDEFINED
                && xcal.getTimezone() != javax.xml.datatype.DatatypeConstants.FIELD_UNDEFINED ) {
                return new org.joda.time.DateTime( xcal.getYear(),
                    xcal.getMonth(),
                    xcal.getDay(),
                    xcal.getHour(),
                    xcal.getMinute(),
                    xcal.getSecond(),
                    org.joda.time.DateTimeZone.forOffsetMillis( xcal.getTimezone() * 60000 )
                );
            }
            else if ( xcal.getSecond() != javax.xml.datatype.DatatypeConstants.FIELD_UNDEFINED ) {
                return new org.joda.time.DateTime( xcal.getYear(),
                    xcal.getMonth(),
                    xcal.getDay(),
                    xcal.getHour(),
                    xcal.getMinute(),
                    xcal.getSecond()
                );
            }
            else if ( xcal.getTimezone() != javax.xml.datatype.DatatypeConstants.FIELD_UNDEFINED ) {
                return new org.joda.time.DateTime( xcal.getYear(),
                    xcal.getMonth(),
                    xcal.getDay(),
                    xcal.getHour(),
                    xcal.getMinute(),
                    org.joda.time.DateTimeZone.forOffsetMillis( xcal.getTimezone() * 60000 )
            );
            }
            else {
                return new org.joda.time.DateTime( xcal.getYear(),
                    xcal.getMonth(),
                    xcal.getDay(),
                    xcal.getHour(),
                    xcal.getMinute()
            );
            }
        }
    return null;
}
