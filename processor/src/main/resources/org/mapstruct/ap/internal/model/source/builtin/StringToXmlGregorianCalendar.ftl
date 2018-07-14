<#--

    Copyright MapStruct Authors.

    Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0

-->
private <@includeModel object=findType("XMLGregorianCalendar")/> ${name}( String date, String dateFormat ) {
    if ( date == null ) {
        return null;
    }

    try {
        if ( dateFormat != null ) {
            <@includeModel object=findType("DateFormat")/> df = new <@includeModel object=findType("SimpleDateFormat")/>( dateFormat );
            <@includeModel object=findType("GregorianCalendar")/> c = new <@includeModel object=findType("GregorianCalendar")/>();
            c.setTime( df.parse( date ) );
            return <@includeModel object=findType("DatatypeFactory")/>.newInstance().newXMLGregorianCalendar( c );
        }
        else {
            return <@includeModel object=findType("DatatypeFactory")/>.newInstance().newXMLGregorianCalendar( date );
        }
    }
    catch ( <@includeModel object=findType("DatatypeConfigurationException")/> ex ) {
        throw new RuntimeException( ex );
    }
    catch ( <@includeModel object=findType("ParseException")/> ex ) {
        throw new RuntimeException( ex );
    }
}
