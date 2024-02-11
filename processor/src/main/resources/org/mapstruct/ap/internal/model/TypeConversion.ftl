<#--

    Copyright MapStruct Authors.

    Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0

-->
<#-- @ftlvariable name="" type="org.mapstruct.ap.internal.model.TypeConversion" -->
<@compress single_line=true>
${openExpression}<@_assignment/>${closeExpression}
<#macro _assignment>
    <@includeModel object=assignment
               targetBeanName=ext.targetBeanName
               existingInstanceMapping=ext.existingInstanceMapping
               targetReadAccessorName=ext.targetReadAccessorName
               targetWriteAccessorName=ext.targetWriteAccessorName
               sourcePropertyName=ext.sourcePropertyName
               targetPropertyName=ext.targetPropertyName
               targetType=ext.targetType/>
</#macro>
</@compress>
