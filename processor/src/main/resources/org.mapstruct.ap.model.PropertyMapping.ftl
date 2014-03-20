<#--

     Copyright 2012-2014 Gunnar Morling (http://www.gunnarmorling.de/)
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
 <#if !( targetType.collectionType || targetType.mapType ) >
    <#-- non collections or maps -->
    <#if ( !sourceType.primitive && parameterAssignment.assignmentType!="ASSIGNMENT" ) >
        if ( ${sourceBeanName}.${sourceAccessorName}() != null ) {
           <@assignmentLine/>
        }
    <#else>
        <@assignmentLine/>
    </#if>
<#else>
    <#-- collections or maps -->
    <#if ( ext.existingInstanceMapping || !targetAccessorSetter ) >
        if ( ${ext.targetBeanName}.${targetReadAccessorName}() != null ) {
            <#if ext.existingInstanceMapping>
                ${ext.targetBeanName}.${targetReadAccessorName}().clear();
             </#if><#t>
             if ( ${sourceBeanName}.${sourceAccessorName}() != null ) {
                <#if targetType.collectionType>
                    <@collectionOrMapAssignmentLine target="${ext.targetBeanName}.${targetReadAccessorName}().addAll"/>
                <#else>
                    <@collectionOrMapAssignmentLine target="${ext.targetBeanName}.${targetReadAccessorName}().putAll"/>
                </#if>
            }
        }
        <#if targetAccessorSetter>
           else if ( ${sourceBeanName}.${sourceAccessorName}() != null ) {
              <@collectionOrMapAssignmentLine/>
           }
        </#if>
    <#elseif targetAccessorSetter>
         if ( ${sourceBeanName}.${sourceAccessorName}() != null ) {
              <@collectionOrMapAssignmentLine/>
         }
     </#if>
 </#if>
 <#macro collectionOrMapAssignmentLine
        target="${ext.targetBeanName}.${targetAccessorName}"
        source="${sourceBeanName}.${sourceAccessorName}">
    <#compress>
         <#if parameterAssignment?? && parameterAssignment.assignmentType!="ASSIGNMENT">
             <@assignmentLine target source/>
         <#else>
             ${target}( new <#if targetType.implementationType??><@includeModel object=targetType.implementationType/><#else><@includeModel object=targetType/></#if>( ${source}() ) );
         </#if>
    </#compress>

</#macro>
<#macro assignmentLine
         target="${ext.targetBeanName}.${targetAccessorName}"
         source="${sourceBeanName}.${sourceAccessorName}">
     <@includeModel object=parameterAssignment target=target source="${source}()" targetType=targetType raw=true/>
</#macro>
