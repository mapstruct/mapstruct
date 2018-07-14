<#--

    Copyright MapStruct Authors.

    Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0

-->
<#if includeAnnotationsOnField>
    <#list annotations as annotation>
        <#nt><@includeModel object=annotation/>
    </#list>
</#if>
private <#if fieldFinal>final </#if><@includeModel object=type/> ${variableName};