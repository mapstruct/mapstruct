<#--

    Copyright MapStruct Authors.

    Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0

-->
<#-- @ftlvariable name="" type="org.mapstruct.ap.internal.model.SupportingMappingMethod" -->
private <@includeModel object=findType("XMLGregorianCalendar")/> ${name}( <@includeModel object=findType("java.time.LocalDateTime")/> localDateTime ) {
    if ( localDateTime == null ) {
        return null;
    }

    return ${supportingField.variableName}.newXMLGregorianCalendar(
        localDateTime.getYear(),
        localDateTime.getMonthValue(),
        localDateTime.getDayOfMonth(),
        localDateTime.getHour(),
        localDateTime.getMinute(),
        localDateTime.getSecond(),
        localDateTime.get( ChronoField.MILLI_OF_SECOND ),
        <@includeModel object=findType("DatatypeConstants")/>.FIELD_UNDEFINED );
}
