<#--

     Copyright 2012-2017 Gunnar Morling (http://www.gunnarmorling.de/)
     and/or other contributors as indicated by the @authors tag. See the
     copyright.txt file in the distribution for a full listing of all
     contributors.

     Licensed under the Apache License, Version 2.0 (the "License");
     you may not use this file except in compliance with the License.
     You may obtain a copy of the License at

         http://www.apache.org/licenses/LICENSE-2.0

     Unless required by applicable law or agreed to in writing, software
     distributed under the License is distributed on an "AS IS" BASIS,
     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
     See the License for the specific language governing permissions and
     limitations under the License.

-->

<#--
  macro: handleNullCheck

  purpose: macro surrounds nested with either a source presence checker or a null check. It always uses
           a local variable. Note that the local variable assignemnt is inside the IF statement for the
           source presence check. Note also, that the else clause contains the default variable assignment if
           present.
-->
<#macro handleNullCheck>
  <#if sourcePresenceChecker??>
    if ( ${sourcePresenceChecker} ) {
      <@includeModel object=localVarType/> ${localVarName} = <@lib.handleAssignment/>;
      <#nested>
    }
  <#else>
    <@includeModel object=localVarType/> ${localVarName} = <@lib.handleAssignment/>;
    if ( ${localVarName} != null ) {
      <#nested>
    }
  </#if>
  <#if ext.defaultValueAssignment?? >
  else {
    <@lib.handeDefaultAssigment/>
  }
  </#if>
</#macro>

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