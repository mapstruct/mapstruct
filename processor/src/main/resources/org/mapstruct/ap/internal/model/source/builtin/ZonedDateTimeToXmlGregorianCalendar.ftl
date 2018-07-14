<#--

    Copyright MapStruct Authors.

    Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0

-->
private <@includeModel object=findType("XMLGregorianCalendar")/> ${name}( <@includeModel object=findType("ZonedDateTime")/> zdt ) {
    if ( zdt == null ) {
        return null;
    }

    try {
        return <@includeModel object=findType("DatatypeFactory")/>.newInstance().newXMLGregorianCalendar( <@includeModel object=findType("GregorianCalendar")/>.from( zdt ) );
    }
    catch ( <@includeModel object=findType("DatatypeConfigurationException")/> ex ) {
        throw new RuntimeException( ex );
    }
}
