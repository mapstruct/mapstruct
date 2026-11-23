<#--

    Copyright MapStruct Authors.

    Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0

-->
<#-- @ftlvariable name="" type="org.mapstruct.ap.internal.model.SupportingMappingMethod" -->
<#list annotations as annotation>
    <#nt><@includeModel object=annotation/>
</#list>
private <@includeModel object=findType("XMLGregorianCalendar") typeAnnotation=typeAnnotation/> ${name}( <@includeModel object=sourceParameters[0]/> ) {
    <@includeModel object=findType("GregorianCalendar")/> gcal = new <@includeModel object=findType("GregorianCalendar")/>( cal.getTimeZone() );
    gcal.setTimeInMillis( cal.getTimeInMillis() );
    return ${supportingField.variableName}.newXMLGregorianCalendar( gcal );
}
