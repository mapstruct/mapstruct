<#-- @ftlvariable name="" type="org.mapstruct.ap.internal.model.MethodReference" -->
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
<@compress single_line=true>
    <#-- method is either internal to the mapper class, or external (via uses) declaringMapper!=null -->
    <#if declaringMapper??>
        <#if static><@includeModel object=declaringMapper.type/><#else>${mapperVariableName}</#if>.<@methodCall/>
    <#-- method is provided by a context parameter  -->
    <#elseif providingParameter??>
        <#if static><@includeModel object=providingParameter.type/><#else>${providingParameter.name}</#if>.<@methodCall/>
    <#-- method is referenced java8 static method in the mapper to implement (interface)  -->
    <#elseif static>
        <@includeModel object=definingType/>.<@methodCall/>
    <#else>
        <@methodCall/>
    </#if>
</@compress>
<#--
  macro:   methodCall
  purpose: the actual method call (stuff following the dot)
-->
<#macro methodCall>
    <#lt>${name}<#if (parameterBindings?size > 0)>( <@arguments/> )<#else>()</#if>
</#macro>
<#--
  macro:   arguments
  purpose: the arguments in the method call
-->
<#macro arguments>
    <#list parameterBindings as param>
        <#if param.targetType>
            <#-- a class is passed on for casting, see @TargetType -->
            <@includeModel object=ext.targetType raw=true/>.class<#t>
        <#elseif param.mappingTarget>
            ${ext.targetBeanName}<#if ext.targetReadAccessorName??>.${ext.targetReadAccessorName}</#if><#t>
        <#elseif param.mappingContext>
            ${param.variableName}<#t>
        <#elseif param.sourceRHS??>
            <@_assignment assignmentToUse=param.sourceRHS/><#t>
        <#elseif assignment??>
            <@_assignment assignmentToUse=assignment/><#t>
        <#else>
            ${param.variableName}<#t>
        </#if>
            <#if param_has_next>, </#if><#t>
        </#list>
    <#-- context parameter, e.g. for builtin methods concerning date conversion -->
    <#if contextParam??>, ${contextParam}</#if><#t>
</#macro>
<#--
  macro:   assignment
  purpose: note: takes its targetyType from the singleSourceParameterType
-->
<#macro _assignment assignmentToUse>
   <@includeModel object=assignmentToUse
        targetBeanName=ext.targetBeanName
               existingInstanceMapping=ext.existingInstanceMapping
               targetReadAccessorName=ext.targetReadAccessorName
               targetWriteAccessorName=ext.targetWriteAccessorName
               targetType=singleSourceParameterType/>
</#macro>