<#--

    Copyright MapStruct Authors.

    Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0

-->
<#-- @ftlvariable name="" type="org.mapstruct.ap.internal.model.assignment.GetterWrapperForCollectionsAndMaps" -->
<#import "../macro/CommonMacros.ftl" as lib>
<@lib.sourceLocalVarAssignment/>
if ( ${ext.targetBeanName}.${ext.targetWriteAccessorName}<@lib.handleWriteAccesing /> != null ) {
    <@lib.handleExceptions>
      <@lib.handleLocalVarNullCheck needs_explicit_local_var=false cleanBefore=true ; sourcePresent>
        <#if sourcePresent??>
          <#if ext.existingInstanceMapping>
            <#if sourcePresent && (ext.targetType.setType || ext.targetType.mapType)>
              ${ext.targetBeanName}.${ext.targetWriteAccessorName}<@lib.handleWriteAccesing />.<#if ext.targetType.mapType>entrySet().</#if>retainAll( <@lib.handleWithAssignmentOrNullCheckVar/><#if sourceType.mapType>.entrySet()</#if> );
            <#else>
              ${ext.targetBeanName}.${ext.targetWriteAccessorName}<@lib.handleWriteAccesing />.clear();
            </#if>
          </#if>
        <#else>
          ${ext.targetBeanName}.${ext.targetWriteAccessorName}<@lib.handleWriteAccesing />.<#if ext.targetType.mapType>putAll<#else>addAll</#if>( <@lib.handleWithAssignmentOrNullCheckVar/> );
        </#if>
      </@lib.handleLocalVarNullCheck>
    </@lib.handleExceptions>
}
