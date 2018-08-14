<#--

    Copyright MapStruct Authors.

    Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0

-->
<#-- @ftlvariable name="" type="org.mapstruct.ap.internal.model.SupportingMappingMethod" -->
private <@includeModel object=findType("XMLGregorianCalendar")/> ${name}( <@includeModel object=findType("ZonedDateTime")/> zdt ) {
    if ( zdt == null ) {
        return null;
    }

    return ${supportingField.variableName}.newXMLGregorianCalendar( <@includeModel object=findType("GregorianCalendar")/>.from( zdt ) );
}
