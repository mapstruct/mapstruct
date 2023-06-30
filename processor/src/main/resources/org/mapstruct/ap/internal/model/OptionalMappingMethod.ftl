<#--

    Copyright MapStruct Authors.

    Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0

-->
<#-- @ftlvariable name="" type="org.mapstruct.ap.internal.model.OptionalMappingMethod" -->
<#if overridden>@Override</#if>
<#lt>${accessibility.keyword} <@includeModel object=returnType/> ${name}(<#list parameters as param><@includeModel object=param/><#if param_has_next>, </#if></#list>)<@throws/> {
    <#list beforeMappingReferencesWithoutMappingTarget as callback>
    	<@includeModel object=callback targetBeanName=resultName targetType=resultType/>
    	<#if !callback_has_next>

    	</#if>
    </#list>
    if ( ${sourceParameter.name} == null ) {
        <#if resultType.optionalType>
            <#-- regardless of mapNullToDefault value, never return null for Optional resultType -->
            return Optional.empty();
        <#else>
            <#if !mapNullToDefault>
                return null;
            <#else>
                <#-- TODO: this should return the default value for whatever the returnType is -->
                return null;
            </#if>
        </#if>
    }

    <#if sourceParameter.type.optionalType>
        <@includeModel object=returnType/> ${resultName} = ${sourceParameter.name}.map( ${loopVariableName} -> <@includeModel object=elementAssignment/> )<#if !returnType.optionalType>.orElse( null )</#if>;
    <#else>
        <@includeModel object=sourceElementType/> ${loopVariableName} = ${sourceParameter.name};
        <@includeModel object=returnType/> ${resultName} = Optional.ofNullable( <@includeModel object=elementAssignment/> );
    </#if>

    <#list afterMappingReferences as callback>
    	<#if callback_index = 0>

    	</#if>
    	<@includeModel object=callback targetBeanName=resultName targetType=resultType/>
    </#list>

    return ${resultName};
}
<#macro throws>
    <#if (thrownTypes?size > 0)><#lt> throws </#if><@compress single_line=true>
        <#list thrownTypes as exceptionType>
            <@includeModel object=exceptionType/>
            <#if exceptionType_has_next>, </#if><#t>
        </#list>
    </@compress>
</#macro>