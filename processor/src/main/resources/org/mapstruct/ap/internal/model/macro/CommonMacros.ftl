<#--

    Copyright MapStruct Authors.

    Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0

-->
<#--
  macro: handleSourceReferenceNullCheck

  purpose: macro surrounds nested with either a source presence checker or a null check. It acts directly on the
           source reference. It adds an else clause with the default assigment when applicable.

           requires: caller to implement boolean:getIncludeSourceNullCheck()
-->
<#macro handleSourceReferenceNullCheck>
    <#if sourcePresenceCheckerReference??>
        if ( ${sourcePresenceCheckerReference} ) {
            <#nested>
        }
        <@elseDefaultAssignment/>
    <#elseif includeSourceNullCheck || ext.defaultValueAssignment??>
        if ( <#if sourceLocalVarName??>${sourceLocalVarName}<#else>${sourceReference}</#if> != null ) {
            <#nested>
        }
        <@elseDefaultAssignment/>
    <#else>
        <#nested>
    </#if>
</#macro>
<#--
    local macro related to handleSourceReferenceNullCheck
    note: the <#elseif setExplicitlyToDefault || setExplicitlyToNull> is only relevant for update mappings
          the default value takes precedence
-->
<#macro elseDefaultAssignment>
    <#if ext.defaultValueAssignment?? >
      else {
        <@handeDefaultAssigment/>
      }
    <#elseif setExplicitlyToDefault || setExplicitlyToNull>
      else {
        <#if ext.targetBeanName?has_content>${ext.targetBeanName}.</#if>${ext.targetWriteAccessorName}<@lib.handleWrite><#if setExplicitlyToDefault><@lib.initTargetObject/><#else>null</#if></@lib.handleWrite>;
      }
    </#if>
</#macro>
<#--
  macro: handleLocalVarNullCheck

  purpose: macro surrounds nested with either a source presence checker or a null check. It always uses
           a local variable. Note that the local variable assignemnt is inside the IF statement for the
           source presence check. Note also, that the else clause contains the default variable assignment if
           present.

           requires: caller to implement String:getNullCheckLocalVarName()
                     caller to implement Type:getNullCheckLocalVarType()
-->
<#macro handleLocalVarNullCheck needs_explicit_local_var>
  <#if sourcePresenceCheckerReference??>
    if ( ${sourcePresenceCheckerReference} ) {
      <#if needs_explicit_local_var>
        <@includeModel object=nullCheckLocalVarType/> ${nullCheckLocalVarName} = <@lib.handleAssignment/>;
        <#nested>
      <#else>
        <#nested>
      </#if>
    }
  <#else>
    <@includeModel object=nullCheckLocalVarType/> ${nullCheckLocalVarName} = <@lib.handleAssignment/>;
    if ( ${nullCheckLocalVarName} != null ) {
      <#nested>
    }
  </#if>
  <#if ext.defaultValueAssignment?? >
  else {
    <@handeDefaultAssigment/>
  }
  </#if>
</#macro>
<#--
    Gives the value that needs to be assigned. If there is a sourcePresenceCheckerReference then a direct
    lib.handleAssignment is done, otherwise nullCheckLocalVarName is used.

    requires:        caller to implement String:getNullCheckLocalVarName()
-->
<#macro handleWithAssignmentOrNullCheckVar><#if sourcePresenceCheckerReference??><@lib.handleAssignment/><#else>${nullCheckLocalVarName}</#if></#macro>
<#--
  macro: handleExceptions

  purpose: Includes the try - catch clauses around the nested code.
-->
<#macro handleExceptions>
  <#if (thrownTypes?size == 0) >
        <#nested>
  <#else>
        try {
            <#nested>
        }
        <#list thrownTypes as exceptionType>
        catch ( <@includeModel object=exceptionType/> e ) {
            throw new RuntimeException( e );
        }
        </#list>
  </#if>
</#macro>
<#--
Performs a standard assignment.
-->
<#macro handleAssignment>
    <@includeModel object=assignment
               targetBeanName=ext.targetBeanName
               existingInstanceMapping=ext.existingInstanceMapping
               targetReadAccessorName=ext.targetReadAccessorName
               targetWriteAccessorName=ext.targetWriteAccessorName
               targetType=ext.targetType/>
</#macro>
<#--
Performs a default assignment with a default value.
-->
<#macro handeDefaultAssigment>
    <@includeModel object=ext.defaultValueAssignment
               targetBeanName=ext.targetBeanName
               existingInstanceMapping=ext.existingInstanceMapping
               targetReadAccessorName=ext.targetReadAccessorName
               targetWriteAccessorName=ext.targetWriteAccessorName
               targetType=ext.targetType
               defaultValue=ext.defaultValue/>
</#macro>
<#--
  macro: handleWrite

  purpose: To handle the writing to a field or using a method. The line is not closed with ';'
-->
<#macro handleWrite><#if fieldAssignment> = <#nested><#else>( <#nested> )</#if></#macro>

<#--
  macro: handleWriteAccesing

  purpose: To handle accesing the write target type
-->
<#macro handleWriteAccesing><#if fieldAssignment><#else>()</#if></#macro>
<#--
  macro: initTargetObject

  purpose: To factorize or construct a new target object
-->
<#macro initTargetObject><@compress single_line=true>
    <#if factoryMethod??>
        <@includeModel object=factoryMethod targetType=ext.targetType/>
    <#else>
        <@constructTargetObject/>
    </#if>
</@compress></#macro>
<#--
  macro: constructTargetObject

  purpose: Either call the constructor of the target object directly or of the implementing type.
-->
<#macro constructTargetObject><@compress single_line=true>
    <#if ext.targetType.implementationType??>
        new <@includeModel object=ext.targetType.implementationType/>()
    <#elseif ext.targetType.arrayType>
        new <@includeModel object=ext.targetType.componentType/>[0]
    <#elseif ext.targetType.sensibleDefault??>
        ${ext.targetType.sensibleDefault}
    <#else>
        new <@includeModel object=ext.targetType/>()
    </#if>
</@compress></#macro>
<#--
  macro: sourceLocalVarAssignment

  purpose: assignment for source local variables. The sourceLocalVarName replaces the sourceReference in the
           assignmentcall.
-->
<#macro sourceLocalVarAssignment>
    <#if sourceLocalVarName??>
      <@includeModel object=sourceType/> ${sourceLocalVarName} = ${sourceReference};
    </#if>
</#macro>