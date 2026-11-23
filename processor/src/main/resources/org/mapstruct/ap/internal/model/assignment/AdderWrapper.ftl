<#--

    Copyright MapStruct Authors.

    Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0

-->
<#-- @ftlvariable name="" type="org.mapstruct.ap.internal.model.assignment.AdderWrapper" -->
<#import "../macro/CommonMacros.ftl" as lib>
<@lib.handleExceptions>
    <@lib.sourceLocalVarAssignment/>
    <@lib.handleSourceReferenceNullCheck>
        for ( <@includeModel object=adderType.typeBound/> ${sourceLoopVarName} : <#if sourceLocalVarName??>${sourceLocalVarName}<#else>${sourceReference}</#if> ) {
          <#if includeElementNullCheck>
          if ( ${sourceLoopVarName} != null ) {
              <#if ext.targetBeanName?has_content>${ext.targetBeanName}.</#if>${ext.targetWriteAccessorName}<@lib.handleWrite><@lib.handleAssignment/></@lib.handleWrite>;
          }
          <#if adderAcceptsNull>
          else {
              <#if ext.targetBeanName?has_content>${ext.targetBeanName}.</#if>${ext.targetWriteAccessorName}<@lib.handleWrite>null</@lib.handleWrite>;
          }
          </#if>
          <#else>
          <#if ext.targetBeanName?has_content>${ext.targetBeanName}.</#if>${ext.targetWriteAccessorName}<@lib.handleWrite><@lib.handleAssignment/></@lib.handleWrite>;
          </#if>
      }
    </@lib.handleSourceReferenceNullCheck>
</@lib.handleExceptions>