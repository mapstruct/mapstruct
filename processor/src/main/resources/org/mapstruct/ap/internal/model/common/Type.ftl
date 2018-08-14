<#--

    Copyright MapStruct Authors.

    Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0

-->
<#-- @ftlvariable name="" type="org.mapstruct.ap.internal.model.common.Type" -->
<@compress single_line=true>
    <#if wildCardExtendsBound>
        ? extends <@includeModel object=typeBound />
    <#elseif wildCardSuperBound>
        ? super <@includeModel object=typeBound />
    <#else>
        <#if ext.asVarArgs!false>${referenceName?remove_ending("[]")}...<#else>${referenceName}</#if></#if><#if (!ext.raw?? && typeParameters?size > 0) ><<#list typeParameters as typeParameter><@includeModel object=typeParameter /><#if typeParameter_has_next>, </#if></#list>>
    </#if>
</@compress>