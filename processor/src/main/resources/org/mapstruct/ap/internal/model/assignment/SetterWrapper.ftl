<#--

    Copyright MapStruct Authors.

    Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0

-->
<#-- @ftlvariable name="" type="org.mapstruct.ap.internal.model.assignment.SetterWrapper" -->
<#import "../macro/CommonMacros.ftl" as lib>
<@lib.handleExceptions>
    <@lib.sourceLocalVarAssignment/>
    <@lib.handleSourceReferenceNullCheck>
        <#if targetVariableName??>
        <@includeModel object=targetType/> ${targetVariableName} = <@lib.handleAssignment/>;
        </#if>
        <@lib.nullCheckWithElseDefaultAssignment needsNullCheck=needsResultNullCheck variableName=targetVariableName!sourceReference>
            <#if ext.targetBeanName?has_content>${ext.targetBeanName}.</#if>${ext.targetWriteAccessorName}<@lib.handleWrite><#if needsResultNullCheck><#if targetVariableName??>${targetVariableName}<#elseif sourceLocalVarName??>${sourceLocalVarName}<#else>${sourceReference}</#if><#else><@lib.handleAssignment/></#if></@lib.handleWrite>;
        </@lib.nullCheckWithElseDefaultAssignment>
    </@lib.handleSourceReferenceNullCheck>
</@lib.handleExceptions>