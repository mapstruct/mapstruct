<#--

    Copyright MapStruct Authors.

    Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0

-->
<#-- @ftlvariable name="" type="org.mapstruct.ap.internal.model.SupportingConstructorFragment" -->
<@compress single_line=true>
    ${variableName} = ${fragment.reflectionMethodName}(
        <@includeModel object=fragment.sourceType raw=true/>.class,
        "${fragment.methodName}",
        <@includeModel object=fragment.annotationClass raw=true/>.class );
</@compress>
