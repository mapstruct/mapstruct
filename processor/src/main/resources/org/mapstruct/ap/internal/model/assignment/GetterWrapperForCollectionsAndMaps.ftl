<#-- @ftlvariable name="" type="org.mapstruct.ap.internal.model.assignment.GetterWrapperForCollectionsAndMaps" -->
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
