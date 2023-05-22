<#--

    Copyright MapStruct Authors.

    Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0

-->
<#-- @ftlvariable name="" type="org.mapstruct.ap.internal.model.source.reflection.PropertyAnnotationReflection" -->
<@compress single_line=true>
    ${fieldName} = <@includeModel object=containingType raw=true/>.class.<#if method>getMethod<#else>getField</#if>(
        "${accessorSimpleName}" ).getAnnotation( <@includeModel object=annotationClass raw=true/>.class );
</@compress>
