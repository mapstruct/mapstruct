<#--

     Copyright 2012-2016 Gunnar Morling (http://www.gunnarmorling.de/)
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
<#import "../macro/CommonMacros.ftl" as lib>
<@lib.handleExceptions>
<#if ( ext.existingInstanceMapping ) >
    if ( ${ext.targetBeanName}.${ext.targetReadAccessorName}() != null ) {
        <@lib.handleNullCheck>
            ${ext.targetBeanName}.${ext.targetReadAccessorName}().clear();
            ${ext.targetBeanName}.${ext.targetReadAccessorName}().<#if ext.targetType.collectionType>addAll<#else>putAll</#if>( ${localVarName} );
        </@lib.handleNullCheck>
        <#if !ext.defaultValueAssignment?? && !sourcePresenceChecker?? && !allwaysIncludeNullCheck>else {<#-- the opposite (defaultValueAssignment) case is handeld inside lib.handleNullCheck -->
          ${ext.targetBeanName}.${ext.targetWriteAccessorName}( null );
        }
        </#if>
        }
    else {
        <@lib.handleNullCheck>
          <#if newCollectionOrMapAssignment??>
             <@_newCollectionOrMapAssignment/>
          <#else>
             ${ext.targetBeanName}.${ext.targetWriteAccessorName}( ${localVarName} );
          </#if>
        </@lib.handleNullCheck>
    }
<#else>
      <@lib.handleNullCheck>
        <#if newCollectionOrMapAssignment??>
          <@_newCollectionOrMapAssignment/>
        <#else>
          ${ext.targetBeanName}.${ext.targetWriteAccessorName}( ${localVarName} );
        </#if>
      </@lib.handleNullCheck>
</#if>
</@lib.handleExceptions>

<#macro _newCollectionOrMapAssignment>
    <@includeModel object=newCollectionOrMapAssignment
            targetBeanName=ext.targetBeanName
            existingInstanceMapping=ext.existingInstanceMapping
            targetReadAccessorName=ext.targetReadAccessorName
            targetWriteAccessorName=ext.targetWriteAccessorName
            targetType=ext.targetType/>
</#macro>
