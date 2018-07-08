<#--

    Copyright MapStruct Authors.

    Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0

-->
<#-- @ftlvariable name="" type="org.mapstruct.ap.internal.model.SupportingMappingMethod" -->
private <@includeModel object=findType("XMLGregorianCalendar")/> ${name}( <@includeModel object=findType("DateTime")/> dt ) {
    if ( dt == null ) {
        return null;
    }
    return ${supportingField.variableName}.newXMLGregorianCalendar(
        dt.getYear(),
        dt.getMonthOfYear(),
        dt.getDayOfMonth(),
        dt.getHourOfDay(),
        dt.getMinuteOfHour(),
        dt.getSecondOfMinute(),
        dt.getMillisOfSecond(),
        dt.getZone().getOffset( null ) / 60000 );
}
