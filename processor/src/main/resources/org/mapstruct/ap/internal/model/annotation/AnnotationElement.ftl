<#--

    Copyright MapStruct Authors.

    Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0

-->
<#-- @ftlvariable name="" type="org.mapstruct.ap.internal.model.annotation.AnnotationElement" -->
<@compress single_line=true>
  <#if elementName??>
    ${elementName} =
  </#if>
  <#if (values?size > 1) >
    {
  </#if>
  <#-- rt and lt tags below are for formatting the arrays so that there are no spaces before ',' -->
  <#list values as value>
    <#if boolean>
      ${value?c}<#rt>
    <#elseif byte>
      ${value}<#rt>
    <#elseif character>
      '${value}'<#rt>
    <#elseif class>
      <@includeModel object=value raw=true/>.class<#rt>
    <#elseif double>
      ${value?c}<#rt>
    <#elseif enum>
      <@includeModel object=value.enumClass raw=true/>.${value.name}<#rt>
    <#elseif float>
      ${value?c}f<#rt>
    <#elseif integer>
      ${value?c}<#rt>
    <#elseif long>
      ${value?c}L<#rt>
    <#elseif short>
      ${value?c}<#rt>
    <#elseif string>
      "${value}"<#rt>
    </#if>
    <#if value_has_next>
      , <#lt>
    </#if>
  </#list>
  <#if (values?size > 1) >
    }
  </#if>
</@compress>