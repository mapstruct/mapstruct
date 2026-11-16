<#--

    Copyright MapStruct Authors.

    Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0

-->
<#-- @ftlvariable name="" type="org.mapstruct.ap.internal.model.common.NewInstanceCreation" -->
<@compress single_line=true>
new <@includeModel object=rawType/><#if generic><></#if>
</@compress>
