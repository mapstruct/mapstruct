<#--

    Copyright MapStruct Authors.

    Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0

-->
@Override
public <@includeModel object=returnType/> ${name}(<#list parameters as param><@includeModel object=param/><#if param_has_next>, </#if></#list>) <@throws/> {
    <#if returnType.name != "void">return </#if>delegate.${name}( <#list parameters as param>${param.name}<#if param_has_next>, </#if></#list> );
}
<#macro throws>
    <@compress single_line=true>
        <#if (thrownTypes?size > 0)>throws </#if>
        <#list thrownTypes as exceptionType>
            <@includeModel object=exceptionType/>
            <#if exceptionType_has_next>, </#if><#t>
        </#list>
    </@compress>
</#macro>