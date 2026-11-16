<#--

    Copyright MapStruct Authors.

    Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0

-->
<#-- @ftlvariable name="" type="org.mapstruct.ap.internal.model.NestedPropertyMappingMethod" -->
<#lt>private <@includeModel object=returnType.typeBound/> ${name}(<#list parameters as param><@includeModel object=param/><#if param_has_next>, </#if></#list>)<@throws/> {
<#list propertyEntries as entry>
    <#if entry.presenceChecker?? >
    if ( <@includeModel object=entry.presenceChecker /> ) {
        return ${returnType.null};
    }
    </#if>
    <#if entry_has_next>
        <@includeModel object=entry.type.typeBound/> ${entry.name} = ${entry.source};
    <#else>
        return ${entry.source};
    </#if>
</#list>
}
<#macro throws>
    <#if (thrownTypes?size > 0)><#lt> throws </#if><@compress single_line=true>
        <#list thrownTypes as exceptionType>
            <@includeModel object=exceptionType/>
            <#if exceptionType_has_next>, </#if><#t>
        </#list>
    </@compress>
</#macro>