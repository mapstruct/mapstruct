<#--

    Copyright MapStruct Authors.

    Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0

-->
<#-- @ftlvariable name="" type="org.mapstruct.ap.internal.model.assignment.SetterWrapperForCollectionsAndMapsWithNullCheck" -->
<#import "../macro/CommonMacros.ftl" as lib>
<@lib.sourceLocalVarAssignment/>
<@lib.handleExceptions>
  <@callTargetWriteAccessor/>
</@lib.handleExceptions>
<#--
  assigns the target via the regular target write accessor (usually the setter)
-->
<#macro callTargetWriteAccessor>
  <@lib.handleLocalVarNullCheck needs_explicit_local_var=directAssignment>
      <#if ext.targetType.implementationType??><@includeModel object=ext.targetType.implementationType/><#else><@includeModel object=ext.targetType/></#if> ${instanceVar} = new <#if ext.targetType.implementationType??><@includeModel object=ext.targetType.implementationType/><#else><@includeModel object=ext.targetType/></#if>();
      ${instanceVar}.<#if ext.targetType.collectionType>addAll<#else>putAll</#if>( ${nullCheckLocalVarName} );
      <#if ext.targetBeanName?has_content>${ext.targetBeanName}.</#if>${ext.targetWriteAccessorName}<@lib.handleWrite>${instanceVar}</@lib.handleWrite>;
  </@lib.handleLocalVarNullCheck>
</#macro>
