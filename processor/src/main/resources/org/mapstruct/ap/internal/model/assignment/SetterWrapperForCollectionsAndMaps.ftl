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
  <#if ext.existingInstanceMapping>
    if ( ${ext.targetBeanName}.${ext.targetReadAccessorName} != null ) {
        <@lib.handleNullCheck>
            ${ext.targetBeanName}.${ext.targetReadAccessorName}.clear();
            ${ext.targetBeanName}.${ext.targetReadAccessorName}.<#if ext.targetType.collectionType>addAll<#else>putAll</#if>( ${localVarName} );
        </@lib.handleNullCheck>
        <#if !ext.defaultValueAssignment?? && !sourcePresenceCheckerReference?? && !allwaysIncludeNullCheck>else {<#-- the opposite (defaultValueAssignment) case is handeld inside lib.handleNullCheck -->
          ${ext.targetBeanName}.${ext.targetWriteAccessorName}<@lib.handleWrite>null</@lib.handleWrite>;
        }
        </#if>
        }
    else {
      <@callTargetWriteAccessor/>
    }
  <#else>
    <@callTargetWriteAccessor/>
  </#if>
</@lib.handleExceptions>
<#--
  assigns the target via the regular target write accessor (usually the setter)
-->
<#macro callTargetWriteAccessor>
    <@lib.handleNullCheck>
        ${ext.targetBeanName}.${ext.targetWriteAccessorName}<@lib.handleWrite><#if directAssignment><@wrapLocalVarInCollectionInitializer/><#else>${localVarName}</#if></@lib.handleWrite>;
  </@lib.handleNullCheck>
</#macro>
<#--
  wraps the local variable in a collection initializer (new collection, or EnumSet.copyOf)
-->
<#macro wrapLocalVarInCollectionInitializer><@compress single_line=true>
    <#if enumSet>
      EnumSet.copyOf( ${localVarName} )
    <#else>
      new <#if ext.targetType.implementationType??><@includeModel object=ext.targetType.implementationType/><#else><@includeModel object=ext.targetType/></#if>( ${localVarName} )
    </#if>
</@compress></#macro>