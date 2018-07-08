<#--

    Copyright MapStruct Authors.

    Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0

-->
<#-- @ftlvariable name="" type="org.mapstruct.ap.internal.model.LifecycleCallbackMethodReference" -->
<@compress single_line=true>
    <#if hasReturnType()>
        <@includeModel object=methodResultType /> ${targetVariableName} =
    </#if>
    <#include 'MethodReference.ftl'>;
</@compress>
<#if hasReturnType()><#nt>
if ( ${targetVariableName} != null ) {
    return<#if methodReturnType.name != "void"> ${targetVariableName}</#if>;
}</#if>
