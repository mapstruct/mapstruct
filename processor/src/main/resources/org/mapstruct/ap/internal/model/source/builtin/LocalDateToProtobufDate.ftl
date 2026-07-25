<#--

    Copyright MapStruct Authors.

    Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0

-->
<#-- @ftlvariable name="" type="org.mapstruct.ap.internal.model.SupportingMappingMethod" -->
private static <@includeModel object=findType("com.google.type.Date")/> ${name}( <@includeModel object=findType("java.time.LocalDate")/> date ) {
    if ( date == null ) {
        return null;
    }

    <@includeModel object=findType("com.google.type.Date")/>.Builder builder = <@includeModel object=findType("com.google.type.Date")/>.newBuilder();
    builder.setYear( date.getYear() );
    builder.setMonth( date.getMonthValue() );
    builder.setDay( date.getDayOfMonth() );
    return builder.build();
}
