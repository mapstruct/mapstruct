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
      <#if ext.targetBeanName?has_content>${ext.targetBeanName}.</#if>${ext.targetWriteAccessorName}<@lib.handleWrite><#if directAssignment><@wrapLocalVarInCollectionInitializer/><#else><@lib.handleWithAssignmentOrNullCheckVar/></#if></@lib.handleWrite>;
  </@lib.handleLocalVarNullCheck>
  <#if !ext.defaultValueAssignment?? && !sourcePresenceCheckerReference?? && !ext.targetBeanName?has_content>else {<#-- the opposite (defaultValueAssignment) case is handeld inside lib.handleLocalVarNullCheck -->
      ${ext.targetWriteAccessorName}<@lib.handleWrite>null</@lib.handleWrite>;
      }
  </#if>
</#macro>
<#--
  wraps the local variable in a collection initializer (new collection, or EnumSet.copyOf)
-->
<#macro wrapLocalVarInCollectionInitializer><@compress single_line=true>
    <#if enumSet>
      EnumSet.copyOf( ${nullCheckLocalVarName} )
    <#else>
      new <#if ext.targetType.implementationType??><@includeModel object=ext.targetType.implementationType/><#else><@includeModel object=ext.targetType/></#if>( ${nullCheckLocalVarName} )
    </#if>
</@compress></#macro>