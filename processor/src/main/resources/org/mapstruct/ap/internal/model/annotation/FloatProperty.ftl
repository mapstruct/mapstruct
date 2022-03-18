<#--

    Copyright MapStruct Authors.

    Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0

-->
<#-- @ftlvariable name="" type="org.mapstruct.ap.internal.model.annotation.FloatProperty" -->
<#if key??>${key} = </#if><#if (values?size > 1) >{ </#if><#list values as value>${value?c}f<#if value_has_next>, </#if></#list><#if (values?size > 1) > }</#if>