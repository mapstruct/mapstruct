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
          <#if ext.targetBeanName?has_content>${ext.targetBeanName}.</#if>${ext.targetWriteAccessorName}<@lib.handleWrite><@lib.handleAssignment/></@lib.handleWrite>;
      }
    </@lib.handleSourceReferenceNullCheck>
</@lib.handleExceptions>