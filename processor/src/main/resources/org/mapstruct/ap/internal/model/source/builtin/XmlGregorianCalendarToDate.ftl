<#--

    Copyright MapStruct Authors.

    Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0

-->
<#-- @ftlvariable name="" type="org.mapstruct.ap.internal.model.SupportingMappingMethod" -->
<#list annotations as annotation>
    <#nt><@includeModel object=annotation/>
</#list>
private static <@includeModel object=findType("java.util.Date") typeAnnotation=typeAnnotation/> ${name}( <@includeModel object=sourceParameters[0]/> ) {
    return xcal.toGregorianCalendar().getTime();
}
