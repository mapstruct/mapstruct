<#--

    Copyright MapStruct Authors.

    Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0

-->
<#-- @ftlvariable name="" type="org.mapstruct.ap.internal.model.SupportingMappingMethod" -->
private static <@includeModel object=findType("java.time.LocalDate")/> ${name}( <@includeModel object=findType("com.google.type.Date")/> date ) {
    if ( date == null ) {
        return null;
    }

    return <@includeModel object=findType("java.time.LocalDate")/>.of( date.getYear(), date.getMonth(), date.getDay() );
}
