<#--

    Copyright MapStruct Authors.

    Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0

-->
private <@includeModel object=findType("XMLGregorianCalendar")/> ${name}( <@includeModel object=findType("org.joda.time.LocalDate")/> dt ) {
    if ( dt == null ) {
        return null;
    }

    try {
        return <@includeModel object=findType("DatatypeFactory")/>.newInstance().newXMLGregorianCalendarDate(
            dt.getYear(),
            dt.getMonthOfYear(),
            dt.getDayOfMonth(),
            <@includeModel object=findType("DatatypeConstants")/>.FIELD_UNDEFINED );
    }
    catch ( <@includeModel object=findType("DatatypeConfigurationException")/> ex ) {
        throw new RuntimeException( ex );
    }
}
