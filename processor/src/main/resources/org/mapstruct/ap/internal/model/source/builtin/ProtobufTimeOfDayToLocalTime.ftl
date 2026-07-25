<#--

    Copyright MapStruct Authors.

    Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0

-->
<#-- @ftlvariable name="" type="org.mapstruct.ap.internal.model.SupportingMappingMethod" -->
private static <@includeModel object=findType("java.time.LocalTime")/> ${name}( <@includeModel object=findType("com.google.type.TimeOfDay")/> timeOfDay ) {
    if ( timeOfDay == null ) {
        return null;
    }

    return <@includeModel object=findType("java.time.LocalTime")/>.of(
        timeOfDay.getHours(),
        timeOfDay.getMinutes(),
        timeOfDay.getSeconds(),
        timeOfDay.getNanos()
    );
}
