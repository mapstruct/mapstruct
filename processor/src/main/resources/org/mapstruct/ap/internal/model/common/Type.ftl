<#--

    Copyright MapStruct Authors.

    Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0

-->
<#-- @ftlvariable name="" type="org.mapstruct.ap.internal.model.common.Type" -->
<@compress single_line=true>
    <#if hasExtendsBound()>
        ? extends <@includeModel object=typeBound />
    <#elseif hasSuperBound()>
        ? super <@includeModel object=typeBound />
    <#else>
        <#if ext.asVarArgs!false>${createReferenceName()?remove_ending("[]")}...<#else>${createReferenceName()}</#if></#if><#if (!ext.raw?? && typeParameters?size > 0) ><<#list typeParameters as typeParameter><@includeModel object=typeParameter /><#if typeParameter_has_next>, </#if></#list>>
    </#if>
</@compress>