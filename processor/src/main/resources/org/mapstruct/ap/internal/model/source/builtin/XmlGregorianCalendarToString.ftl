<#--

    Copyright MapStruct Authors.

    Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0

-->
<#-- @ftlvariable name="" type="org.mapstruct.ap.internal.model.SupportingMappingMethod" -->
private String ${name}( <@includeModel object=findType("XMLGregorianCalendar")/> xcal, String dateFormat ) {
    if ( xcal == null ) {
        return null;
    }

    if (dateFormat == null ) {
        return xcal.toString();
    }
    else {
        <@includeModel object=findType("java.util.Date")/> d = xcal.toGregorianCalendar().getTime();
        <@includeModel object=findType("SimpleDateFormat")/> sdf = new <@includeModel object=findType("SimpleDateFormat")/>( dateFormat );
        return sdf.format( d );
    }
}
