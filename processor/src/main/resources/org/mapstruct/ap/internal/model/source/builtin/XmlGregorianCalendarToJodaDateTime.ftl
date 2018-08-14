<#--

    Copyright MapStruct Authors.

    Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0

-->
<#-- @ftlvariable name="" type="org.mapstruct.ap.internal.model.SupportingMappingMethod" -->
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
