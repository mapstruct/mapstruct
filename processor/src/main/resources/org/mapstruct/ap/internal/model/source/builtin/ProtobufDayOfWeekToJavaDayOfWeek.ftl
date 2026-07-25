<#--

    Copyright MapStruct Authors.

    Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0

-->
<#-- @ftlvariable name="" type="org.mapstruct.ap.internal.model.SupportingMappingMethod" -->
private static <@includeModel object=findType("java.time.DayOfWeek")/> ${name}( <@includeModel object=findType("com.google.type.DayOfWeek")/> dayOfWeek ) {
    if ( dayOfWeek == null || dayOfWeek == <@includeModel object=findType("com.google.type.DayOfWeek")/>.DAY_OF_WEEK_UNSPECIFIED ) {
        return null;
    }

    return <@includeModel object=findType("java.time.DayOfWeek")/>.of( dayOfWeek.getNumber() );
}
