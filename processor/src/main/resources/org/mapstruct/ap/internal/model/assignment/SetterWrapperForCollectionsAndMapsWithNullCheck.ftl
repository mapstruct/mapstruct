<#-- @ftlvariable name="" type="org.mapstruct.ap.internal.model.assignment.SetterWrapperForCollectionsAndMapsWithNullCheck" -->
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
<#import "../macro/CommonMacros.ftl" as lib>
<@lib.sourceLocalVarAssignment/>
<@lib.handleExceptions>
  <@callTargetWriteAccessor/>
  <#if !ext.defaultValueAssignment??>else {<#-- the opposite (defaultValueAssignment) case is handeld inside lib.handleLocalVarNullCheck -->
    ${ext.targetBeanName}.${ext.targetWriteAccessorName}<@lib.handleWrite>null</@lib.handleWrite>;
  }
  </#if>
</@lib.handleExceptions>
<#--
  assigns the target via the regular target write accessor (usually the setter)
-->
<#macro callTargetWriteAccessor>
  <@lib.handleLocalVarNullCheck needs_explicit_local_var=directAssignment>
    ${ext.targetBeanName}.${ext.targetWriteAccessorName}<@lib.handleWrite><#if directAssignment><@wrapLocalVarInCollectionInitializer/><#else><@lib.handleWithAssignmentOrNullCheckVar/></#if></@lib.handleWrite>;
  </@lib.handleLocalVarNullCheck>
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