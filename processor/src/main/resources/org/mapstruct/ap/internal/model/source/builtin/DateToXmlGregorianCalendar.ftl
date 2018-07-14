<#--

    Copyright MapStruct Authors.

    Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0

-->
private <@includeModel object=findType("XMLGregorianCalendar")/> ${name}( <@includeModel object=findType("Date")/> date ) {
    if ( date == null ) {
        return null;
    }

    try {
        <@includeModel object=findType("GregorianCalendar")/> c = new <@includeModel object=findType("GregorianCalendar")/>();
        c.setTime( date );
        return <@includeModel object=findType("DatatypeFactory")/>.newInstance().newXMLGregorianCalendar( c );
    }
    catch ( <@includeModel object=findType("DatatypeConfigurationException")/> ex ) {
        throw new RuntimeException( ex );
    }
}
