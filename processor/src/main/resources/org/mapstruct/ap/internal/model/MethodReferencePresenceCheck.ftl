<#--

    Copyright MapStruct Authors.

    Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0

-->
<#-- @ftlvariable name="" type="org.mapstruct.ap.internal.model.MethodReferencePresenceCheck" -->
<#if isNegate()>!</#if><@includeModel object=methodReference
               presenceCheck=true
               sourcePropertyName=ext.sourcePropertyName
               targetPropertyName=ext.targetPropertyName
               targetType=ext.targetType/>
