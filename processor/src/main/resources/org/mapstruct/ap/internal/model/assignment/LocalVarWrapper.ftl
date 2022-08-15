<#--

    Copyright MapStruct Authors.

    Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0

-->
<#-- @ftlvariable name="" type="org.mapstruct.ap.internal.model.assignment.LocalVarWrapper" -->
<#if (thrownTypes?size == 0) >
    <#if !ext.isTargetDefined?? ><@includeModel object=ext.targetType/></#if> ${ext.targetWriteAccessorName} = <@_assignment/>;
<#else>
    <#if !ext.isTargetDefined?? ><@includeModel object=ext.targetType/> ${ext.targetWriteAccessorName};</#if>
    try {
        ${ext.targetWriteAccessorName} = <@_assignment/>;
    }
    <#list thrownTypes as exceptionType>
    catch ( <@includeModel object=exceptionType/> e ) {
       throw new RuntimeException( e );
    }
    </#list>
</#if>
<#macro _assignment>
    <@includeModel object=assignment
               targetBeanName=ext.targetBeanName
               existingInstanceMapping=ext.existingInstanceMapping
               targetReadAccessorName=ext.targetReadAccessorName
               targetWriteAccessorName=ext.targetWriteAccessorName
               targetPropertyName=ext.targetPropertyName
               targetType=ext.targetType/>
</#macro>