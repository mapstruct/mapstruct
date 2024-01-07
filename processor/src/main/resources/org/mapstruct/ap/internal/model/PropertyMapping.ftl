<#--

    Copyright MapStruct Authors.

    Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0

-->
<#-- @ftlvariable name="" type="org.mapstruct.ap.internal.model.PropertyMapping" -->
<@includeModel object=assignment
               targetBeanName=ext.targetBeanName
               existingInstanceMapping=ext.existingInstanceMapping
               targetReadAccessorName=targetReadAccessorName
               targetWriteAccessorName=targetWriteAccessorName
               sourcePropertyName=sourcePropertyName
               targetPropertyName=name
               targetType=targetType
               defaultValueAssignment=defaultValueAssignment />