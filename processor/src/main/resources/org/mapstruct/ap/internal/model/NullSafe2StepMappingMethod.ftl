<#--

    Copyright MapStruct Authors.

    Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0

-->
<#-- @ftlvariable name="" type="org.mapstruct.ap.internal.model.NullSafe2StepMappingMethod" -->
<#list annotations as annotation>
    <#nt><@includeModel object=annotation/>
</#list>
private <@includeModel object=returnType.typeBound typeAnnotation=typeAnnotation> </@includeModel> ${name}(<#list parameters as param><@includeModel object=param/><#if param_has_next>, </#if></#list>)<@throws/> {
    <#if first.needsParameterNullCheck()>
    if ( ${first.sourceReference} == null ) {
        return ${returnType.null};
    }
    </#if>
    <@includeModel object=intermediateType/> ${secondVariableName} = <@includeModel object=first></@includeModel>;
    if (${secondVariableName} == null) {
        return ${returnType.null};
    }
    return <@includeModel object=second></@includeModel>;
}

<#macro throws>
    <#if (thrownTypes?size > 0)><#lt> throws </#if><@compress single_line=true>
    <#list thrownTypes as exceptionType>
        <@includeModel object=exceptionType/>
        <#if exceptionType_has_next>, </#if><#t>
    </#list>
</@compress>
</#macro>