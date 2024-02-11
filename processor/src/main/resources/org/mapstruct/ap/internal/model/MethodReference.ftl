<#--

    Copyright MapStruct Authors.

    Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0

-->
<#-- @ftlvariable name="" type="org.mapstruct.ap.internal.model.MethodReference" -->
<@compress single_line=true>
    <#-- method is either internal to the mapper class, or external (via uses) declaringMapper!=null -->
    <#if declaringMapper??>
        <#if static><@includeModel object=declaringMapper.type/><#else>${mapperVariableName}</#if>.<@methodCall/>
    <#-- method is provided by a context parameter  -->
    <#elseif providingParameter??>
        <#if static><@includeModel object=providingParameter.type/><#else>${providingParameter.name}</#if>.<@methodCall/>
    <#-- method is referenced java8 static method in the mapper to implement (interface)  -->
    <#elseif static>
        <@includeModel object=definingType raw=true/>.<@methodCall/>
    <#elseif constructor>
        new <@includeModel object=definingType/><#if (parameterBindings?size > 0)>( <@arguments/> )<#else>()</#if>
    <#elseif methodChaining>
        <#list methodsToChain as methodToChain><@includeModel object=methodToChain /><#if methodToChain_has_next>.</#if></#list>
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
            <@includeModel object=inferTypeWhenEnum( ext.targetType ) raw=true/>.class<#t>
        <#elseif param.mappingTarget>
            <#if ext.targetBeanName??>${ext.targetBeanName}<#else>${param.variableName}</#if><#if ext.targetReadAccessorName??>.${ext.targetReadAccessorName}</#if><#t>
        <#elseif param.mappingContext>
            ${param.variableName}<#t>
        <#elseif param.sourcePropertyName>
            "${ext.sourcePropertyName}"<#t>
        <#elseif param.targetPropertyName>
            "${ext.targetPropertyName}"<#t>
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
  purpose: note: takes its targetType from the singleSourceParameterType
-->
<#macro _assignment assignmentToUse>
   <@includeModel object=assignmentToUse
        presenceCheck=ext.presenceCheck
        targetBeanName=ext.targetBeanName
               existingInstanceMapping=ext.existingInstanceMapping
               targetReadAccessorName=ext.targetReadAccessorName
               targetWriteAccessorName=ext.targetWriteAccessorName
               sourcePropertyName=ext.sourcePropertyName
               targetPropertyName=ext.targetPropertyName
               targetType=singleSourceParameterType/>
</#macro>
