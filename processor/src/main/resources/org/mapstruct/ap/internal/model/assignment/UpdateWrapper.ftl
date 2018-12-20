<#--

    Copyright MapStruct Authors.

    Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0

-->
<#-- @ftlvariable name="" type="org.mapstruct.ap.internal.model.assignment.UpdateWrapper" -->
<#import '../macro/CommonMacros.ftl' as lib >
<@lib.handleExceptions>
  <#if includeSourceNullCheck>
    <@lib.sourceLocalVarAssignment/>
    if ( <#if sourcePresenceCheckerReference?? >${sourcePresenceCheckerReference}<#else><#if sourceLocalVarName??>${sourceLocalVarName}<#else>${sourceReference}</#if> != null</#if> ) {
      <@assignToExistingTarget/>
      <@lib.handleAssignment/>;
    }
    <#if mapNullToDefault || includeExplicitNullWhenSourceIsNull>
    else {
      ${ext.targetBeanName}.${ext.targetWriteAccessorName}<@lib.handleWrite><#if mapNullToDefault><@lib.initTargetObject/><#else>null</#if></@lib.handleWrite>;
    }
    </#if>
  <#else>
    <@assignToExistingTarget/>
    <@lib.handleAssignment/>;
  </#if>
</@lib.handleExceptions>
<#--
    target innner check and assignment
-->
<#macro assignToExistingTarget>
    if ( ${ext.targetBeanName}.${ext.targetReadAccessorName} == null ) {
        ${ext.targetBeanName}.${ext.targetWriteAccessorName}<@lib.handleWrite><@lib.initTargetObject/></@lib.handleWrite>;
    }
</#macro>
