<#--

    Copyright MapStruct Authors.

    Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0

-->
private <@includeModel object=findType("XMLGregorianCalendar")/> ${name}( <@includeModel object=findType("Calendar")/> cal ) {
    if ( cal == null ) {
        return null;
    }

    try {
        <@includeModel object=findType("GregorianCalendar")/> gcal = new <@includeModel object=findType("GregorianCalendar")/>( cal.getTimeZone() );
        gcal.setTimeInMillis( cal.getTimeInMillis() );
        return <@includeModel object=findType("DatatypeFactory")/>.newInstance().newXMLGregorianCalendar( gcal );
    }
    catch ( <@includeModel object=findType("DatatypeConfigurationException")/> ex ) {
        throw new RuntimeException( ex );
    }
}
