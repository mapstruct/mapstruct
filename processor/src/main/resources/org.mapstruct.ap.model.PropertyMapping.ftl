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
    <@assignment aTargetType=targetType/>
<#else>
    <#-- collections or maps -->
    <#if ( ext.existingInstanceMapping || !targetAccessorSetter ) >
        if ( ${ext.targetBeanName}.${targetReadAccessorName}() != null ) {
            <#if ext.existingInstanceMapping>
                ${ext.targetBeanName}.${targetReadAccessorName}().clear();
             </#if><#t>
                <#if targetType.collectionType>
                    <@assignment aTarget="${ext.targetBeanName}.${targetReadAccessorName}().addAll"/>
                <#else>
                    <@assignment aTarget="${ext.targetBeanName}.${targetReadAccessorName}().putAll"/>
                </#if>
        }
        <#if targetAccessorSetter>
           else <@assignment/>
        </#if>
    <#elseif targetAccessorSetter>
           <@assignment/>
     </#if>
 </#if>
 <#macro assignment aTarget="${ext.targetBeanName}.${targetAccessorName}" aTargetType=targetType>
         <@includeModel object=propertyAssignment target=aTarget targetType=aTargetType raw=true/>
</#macro>
