<#--

    Copyright MapStruct Authors.

    Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0

-->
<#-- @ftlvariable name="" type="org.mapstruct.ap.internal.model.Annotation" -->
@<@includeModel object=type/><#if (properties?size > 0) >(<#list properties as property>${property}<#if property_has_next>, </#if></#list>)</#if>