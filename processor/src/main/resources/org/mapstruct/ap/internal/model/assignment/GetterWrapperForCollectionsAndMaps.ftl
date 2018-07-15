<#--

    Copyright MapStruct Authors.

    Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0

-->
<#import "../macro/CommonMacros.ftl" as lib>
<@lib.sourceLocalVarAssignment/>
if ( ${ext.targetBeanName}.${ext.targetWriteAccessorName}<@lib.handleWriteAccesing /> != null ) {
    <@lib.handleExceptions>
      <#if ext.existingInstanceMapping>
        ${ext.targetBeanName}.${ext.targetWriteAccessorName}<@lib.handleWriteAccesing />.clear();
      </#if>
      <@lib.handleLocalVarNullCheck needs_explicit_local_var=false>
        ${ext.targetBeanName}.${ext.targetWriteAccessorName}<@lib.handleWriteAccesing />.<#if ext.targetType.collectionType>addAll<#else>putAll</#if>( <@lib.handleWithAssignmentOrNullCheckVar/> );
      </@lib.handleLocalVarNullCheck>
    </@lib.handleExceptions>
}
