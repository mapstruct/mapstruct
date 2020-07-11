<#--

    Copyright MapStruct Authors.

    Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0

-->
<#assign sourceVarName><#if assignment.sourceLocalVarName?? >${assignment.sourceLocalVarName}<#else>${assignment.sourceReference}</#if></#assign>
<#-- @ftlvariable name="" type="org.mapstruct.ap.internal.model.assignment.SetterWrapper" -->
<#import "../macro/CommonMacros.ftl" as lib>
<@lib.handleExceptions>
    <@lib.sourceLocalVarAssignment/>
    <@lib.handleSourceReferenceNullCheck>
        <#if ext.condition??>
        if ( ${ext.condition}( ${sourceVarName?keep_before(".")} ) ) {
        </#if>
          <#if ext.targetBeanName?has_content>${ext.targetBeanName}.</#if>${ext.targetWriteAccessorName}<@lib.handleWrite><@lib.handleAssignment/></@lib.handleWrite>;
        <#if ext.condition??>
        }
        </#if>
    </@lib.handleSourceReferenceNullCheck>
</@lib.handleExceptions>