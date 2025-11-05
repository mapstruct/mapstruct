<#--

    Copyright MapStruct Authors.

    Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0

-->
<#-- @ftlvariable name="" type="org.mapstruct.ap.internal.model.assignment.PutterWrapper" -->
<#import "../macro/CommonMacros.ftl" as lib>
<@lib.handleExceptions>
    <@lib.sourceLocalVarAssignment/>
    <@lib.handleSourceReferenceNullCheck>
        for ( java.util.Map.Entry<<@includeModel object=keyType.typeBound/>, <@includeModel object=valueType.typeBound/>> ${entryVarName} : <#if sourceLocalVarName??>${sourceLocalVarName}<#else>${sourceReference}</#if>.entrySet() ) {
            <#if ext.targetBeanName?has_content>${ext.targetBeanName}.</#if>${ext.targetWriteAccessorName}( ${entryVarName}.getKey(), ${entryVarName}.getValue() );
        }
    </@lib.handleSourceReferenceNullCheck>
</@lib.handleExceptions>
