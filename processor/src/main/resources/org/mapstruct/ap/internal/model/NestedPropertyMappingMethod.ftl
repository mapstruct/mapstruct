<#--

    Copyright MapStruct Authors.

    Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0

-->
<#lt>private <@includeModel object=returnType/> ${name}(<#list parameters as param><@includeModel object=param/><#if param_has_next>, </#if></#list>)<@throws/> {
    if ( ${sourceParameter.name} == null ) {
        return ${returnType.null};
    }
<#list propertyEntries as entry>
    <#if entry.presenceCheckerName?? >
    if ( !<@localVarName index=entry_index/>.${entry.presenceCheckerName}() ) {
        return ${returnType.null};
    }
    </#if>
    <@includeModel object=entry.type/> ${entry.name} = <@localVarName index=entry_index/>.${entry.accessorName};
    <#if !entry.presenceCheckerName?? >
    <#if !entry.type.primitive>
    if ( ${entry.name} == null ) {
        return ${returnType.null};
    }
    </#if>
    </#if>
    <#if !entry_has_next>
    return ${entry.name};
    </#if>
</#list>
}
<#macro localVarName index><#if index == 0>${sourceParameter.name}<#else>${propertyEntries[index-1].name}</#if></#macro>
<#macro throws>
    <#if (thrownTypes?size > 0)><#lt> throws </#if><@compress single_line=true>
        <#list thrownTypes as exceptionType>
            <@includeModel object=exceptionType/>
            <#if exceptionType_has_next>, </#if><#t>
        </#list>
    </@compress>
</#macro>