<#--

    Copyright MapStruct Authors.

    Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0

-->
<#-- @ftlvariable name="" type="org.mapstruct.ap.internal.model.SupportingMappingMethod" -->
private <@includeModel object=findType("XMLGregorianCalendar")/> ${name}( String date, String dateFormat ) {
    if ( date == null ) {
        return null;
    }

    try {
        if ( dateFormat != null ) {
            <@includeModel object=findType("DateFormat")/> df = new <@includeModel object=findType("SimpleDateFormat")/>( dateFormat );
            <@includeModel object=findType("GregorianCalendar")/> c = new <@includeModel object=findType("GregorianCalendar")/>();
            c.setTime( df.parse( date ) );
            return ${supportingField.variableName}.newXMLGregorianCalendar( c );
        }
        else {
            return ${supportingField.variableName}.newXMLGregorianCalendar( date );
        }
    }
    catch ( <@includeModel object=findType("ParseException")/> ex ) {
        throw new RuntimeException( ex );
    }
}
