<#--

    Copyright MapStruct Authors.

    Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0

-->
<#-- @ftlvariable name="" type="org.mapstruct.ap.internal.model.assignment.ArrayCopyWrapper" -->
<#import "../macro/CommonMacros.ftl" as lib>
<@lib.handleExceptions>
    <@lib.sourceLocalVarAssignment/>
    <@lib.handleSourceReferenceNullCheck>
        <#if ext.targetBeanName?has_content>${ext.targetBeanName}.</#if>${ext.targetWriteAccessorName}<@lib.handleWrite>Arrays.copyOf( ${sourceLocalVarName}, ${sourceLocalVarName}.length )</@lib.handleWrite>;
    </@lib.handleSourceReferenceNullCheck>
    <#if !ext.defaultValueAssignment?? && !sourcePresenceCheckerReference?? && !ext.targetBeanName?has_content>else {<#-- the opposite (defaultValueAssignment) case is handeld inside lib.handleSourceReferenceNullCheck -->
        ${ext.targetWriteAccessorName}<@lib.handleWrite>null</@lib.handleWrite>;
        }
    </#if>
</@lib.handleExceptions>