<#--

    Copyright MapStruct Authors.

    Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0

-->
private static <@includeModel object=findType("XMLGregorianCalendar")/> ${name}( <@includeModel object=findType("java.time.LocalDate")/> localDate ) {
    if ( localDate == null ) {
        return null;
    }

    try {
        return <@includeModel object=findType("DatatypeFactory")/>.newInstance().newXMLGregorianCalendarDate(
            localDate.getYear(),
            localDate.getMonthValue(),
            localDate.getDayOfMonth(),
            <@includeModel object=findType("DatatypeConstants")/>.FIELD_UNDEFINED
        );
    }
    catch ( <@includeModel object=findType("DatatypeConfigurationException")/> ex ) {
        throw new RuntimeException( ex );
    }
}
