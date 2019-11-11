<#--

    Copyright MapStruct Authors.

    Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0

-->
<#-- @ftlvariable name="" type="org.mapstruct.ap.internal.model.SupportingMappingMethod" -->
private static <@includeModel object=findType("java.time.LocalDateTime")/> ${name}( <@includeModel object=findType("XMLGregorianCalendar")/> xcal ) {
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
                && xcal.getMillisecond() != <@includeModel object=findType("DatatypeConstants")/>.FIELD_UNDEFINED ) {
                return <@includeModel object=findType("java.time.LocalDateTime")/>.of(
                    xcal.getYear(),
                    xcal.getMonth(),
                    xcal.getDay(),
                    xcal.getHour(),
                    xcal.getMinute(),
                    xcal.getSecond(),
                    Duration.ofMillis( xcal.getMillisecond() ).getNano()
                );
            }
            else if ( xcal.getSecond() != <@includeModel object=findType("DatatypeConstants")/>.FIELD_UNDEFINED ) {
                return <@includeModel object=findType("java.time.LocalDateTime")/>.of(
                    xcal.getYear(),
                    xcal.getMonth(),
                    xcal.getDay(),
                    xcal.getHour(),
                    xcal.getMinute(),
                    xcal.getSecond()
                );
            }
            else {
                return <@includeModel object=findType("java.time.LocalDateTime")/>.of(
                    xcal.getYear(),
                    xcal.getMonth(),
                    xcal.getDay(),
                    xcal.getHour(),
                    xcal.getMinute()
                );
            }
        }
    return null;
}
