<#--

    Copyright MapStruct Authors.

    Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0

-->
<#-- @ftlvariable name="" type="org.mapstruct.ap.internal.model.SupportingMappingMethod" -->
<#list annotations as annotation>
    <#nt><@includeModel object=annotation/>
</#list>
private <T> <#if typeAnnotation??>@<@includeModel object=typeAnnotation/> </#if>T ${name}( <@includeModel object=sourceParameters[0]/> ) {
    if ( element == null ) {
        return null;
    }

    return element.isNil() ? null : element.getValue();
}
