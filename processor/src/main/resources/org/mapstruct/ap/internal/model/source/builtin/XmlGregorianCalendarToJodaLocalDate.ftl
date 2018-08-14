<#--

    Copyright MapStruct Authors.

    Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0

-->
<#-- @ftlvariable name="" type="org.mapstruct.ap.internal.model.SupportingMappingMethod" -->
private static <@includeModel object=findType("org.joda.time.LocalDate")/> ${name}( <@includeModel object=findType("XMLGregorianCalendar")/> xcal ) {
    if ( xcal == null ) {
        return null;
    }

    if ( xcal.getYear() != <@includeModel object=findType("DatatypeConstants")/>.FIELD_UNDEFINED
        && xcal.getMonth() != <@includeModel object=findType("DatatypeConstants")/>.FIELD_UNDEFINED
        && xcal.getDay() != <@includeModel object=findType("DatatypeConstants")/>.FIELD_UNDEFINED )  {
            return new <@includeModel object=findType("org.joda.time.LocalDate")/>( xcal.getYear(), xcal.getMonth(), xcal.getDay() );
    }

    return null;
}
