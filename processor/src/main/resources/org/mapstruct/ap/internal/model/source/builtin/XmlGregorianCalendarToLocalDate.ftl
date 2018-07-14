<#--

    Copyright MapStruct Authors.

    Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0

-->
private static <@includeModel object=findType("java.time.LocalDate")/> ${name}( <@includeModel object=findType("XMLGregorianCalendar")/> xcal ) {
    if ( xcal == null ) {
        return null;
    }

    return <@includeModel object=findType("java.time.LocalDate")/>.of( xcal.getYear(), xcal.getMonth(), xcal.getDay() );
}
