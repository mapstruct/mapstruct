<#--

    Copyright MapStruct Authors.

    Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0

-->
<#-- @ftlvariable name="" type="org.mapstruct.ap.internal.model.SupportingMappingMethod" -->
private <@includeModel object=findType("XMLGregorianCalendar")/> ${name}( <@includeModel object=findType("Calendar")/> cal ) {
    if ( cal == null ) {
        return null;
    }

    <@includeModel object=findType("GregorianCalendar")/> gcal = new <@includeModel object=findType("GregorianCalendar")/>( cal.getTimeZone() );
    gcal.setTimeInMillis( cal.getTimeInMillis() );
    return ${supportingField.variableName}.newXMLGregorianCalendar( gcal );
}
