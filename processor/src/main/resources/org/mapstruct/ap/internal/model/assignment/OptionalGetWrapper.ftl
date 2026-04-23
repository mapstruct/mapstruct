<#--

    Copyright MapStruct Authors.

    Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0

-->
<#-- @ftlvariable name="" type="org.mapstruct.ap.internal.model.assignment.OptionalGetWrapper" -->
<@compress single_line=true>
<#if optionalType.optionalBaseType.isPrimitive()>
${assignment}.getAs${optionalType.optionalBaseType.name?cap_first}()
<#else>
${assignment}.get()
</#if>
</@compress>