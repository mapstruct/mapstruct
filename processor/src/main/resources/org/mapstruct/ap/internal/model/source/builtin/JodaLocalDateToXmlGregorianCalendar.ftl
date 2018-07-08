<#--

    Copyright MapStruct Authors.

    Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0

-->
<#-- @ftlvariable name="" type="org.mapstruct.ap.internal.model.SupportingMappingMethod" -->
private <@includeModel object=findType("XMLGregorianCalendar")/> ${name}( <@includeModel object=findType("org.joda.time.LocalDate")/> dt ) {
    if ( dt == null ) {
        return null;
    }

    return ${supportingField.variableName}.newXMLGregorianCalendarDate(
        dt.getYear(),
        dt.getMonthOfYear(),
        dt.getDayOfMonth(),
        <@includeModel object=findType("DatatypeConstants")/>.FIELD_UNDEFINED );
}
