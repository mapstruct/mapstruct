<#--

    Copyright MapStruct Authors.

    Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0

-->
<#-- @ftlvariable name="" type="org.mapstruct.ap.internal.model.common.InitDefaultValue" -->
<#if factoryMethod??>
    <@includeModel object=factoryMethod targetType=targetType/>
<#else>
    <@constructTargetObject/>
</#if>
<#--
  macro: constructTargetObject

  purpose: Either call the constructor of the target object directly or of the implementing type.
-->
<#macro constructTargetObject><@compress single_line=true>
    <#if targetType.implementationType?? && targetType.implementationType.hasAccessibleDefaultConstructor()>
        new <@includeModel object=targetType.implementationType/>()
    <#elseif targetType.arrayType>
        new <@includeModel object=targetType.componentType/>[0]
    <#elseif targetType.sensibleDefault??>
        ${targetType.sensibleDefault}
    <#elseif targetType.hasAccessibleDefaultConstructor()>
        new <@includeModel object=targetType/>()
    <#else>
        null
    </#if>
</@compress></#macro>