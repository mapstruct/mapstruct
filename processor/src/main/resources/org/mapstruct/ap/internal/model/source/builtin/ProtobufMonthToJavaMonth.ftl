<#--

    Copyright MapStruct Authors.

    Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0

-->
<#-- @ftlvariable name="" type="org.mapstruct.ap.internal.model.SupportingMappingMethod" -->
private static <@includeModel object=findType("java.time.Month")/> ${name}( <@includeModel object=findType("com.google.type.Month")/> month ) {
    if ( month == null || month == <@includeModel object=findType("com.google.type.Month")/>.MONTH_UNSPECIFIED ) {
        return null;
    }

    return <@includeModel object=findType("java.time.Month")/>.of( month.getNumber() );
}
