<#--

    Copyright MapStruct Authors.

    Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0

-->
<#-- @ftlvariable name="" type="org.mapstruct.ap.internal.model.SupportingMappingMethod" -->
private <@includeModel object=findType("XMLGregorianCalendar")/> ${name}( <@includeModel object=findType("Date")/> date ) {
    if ( date == null ) {
        return null;
    }

    <@includeModel object=findType("GregorianCalendar")/> c = new <@includeModel object=findType("GregorianCalendar")/>();
    c.setTime( date );
    return ${supportingField.variableName}.newXMLGregorianCalendar( c );
}
