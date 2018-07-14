<#--

    Copyright MapStruct Authors.

    Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0

-->
private static <@includeModel object=findType("org.joda.time.LocalTime")/> ${name}( <@includeModel object=findType("XMLGregorianCalendar")/> xcal ) {
    if ( xcal == null ) {
        return null;
    }

    if ( xcal.getHour() != <@includeModel object=findType("DatatypeConstants")/>.FIELD_UNDEFINED
         && xcal.getMinute() != <@includeModel object=findType("DatatypeConstants")/>.FIELD_UNDEFINED ) {
            if ( xcal.getSecond() != <@includeModel object=findType("DatatypeConstants")/>.FIELD_UNDEFINED
                && xcal.getMillisecond() != <@includeModel object=findType("DatatypeConstants")/>.FIELD_UNDEFINED ) {
                return new <@includeModel object=findType("org.joda.time.LocalTime")/>( xcal.getHour(),
                    xcal.getMinute(),
                    xcal.getSecond(),
                    xcal.getMillisecond()
                );
            }
            else if ( xcal.getSecond() != <@includeModel object=findType("DatatypeConstants")/>.FIELD_UNDEFINED ) {
                return new <@includeModel object=findType("org.joda.time.LocalTime")/>(
                    xcal.getHour(),
                    xcal.getMinute(),
                    xcal.getSecond()
                );
            }
            else {
                return new <@includeModel object=findType("org.joda.time.LocalTime")/>( xcal.getHour(),
                    xcal.getMinute()
            );
            }
        }
    return null;
}
