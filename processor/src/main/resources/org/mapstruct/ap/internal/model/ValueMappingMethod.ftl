<#--

    Copyright MapStruct Authors.

    Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0

-->
<#-- @ftlvariable name="" type="org.mapstruct.ap.internal.model.ValueMappingMethod" -->
<#list annotations as annotation>
    <#nt><@includeModel object=annotation/>
</#list>
<#if overridden>@Override</#if>
<#lt>${accessibility.keyword} <@includeModel object=returnType/> ${name}(<#list parameters as param><@includeModel object=param/><#if param_has_next>, </#if></#list>)<@throws/> {
    <#list beforeMappingReferencesWithoutMappingTarget as callback>
        <@includeModel object=callback targetBeanName=resultName targetType=resultType/>
        <#if !callback_has_next>

        </#if>
    </#list>
    if ( ${sourceParameter.name} == null ) {
        <#if nullTarget.targetAsException>throw new <@includeModel object=unexpectedValueMappingException />( "Unexpected enum constant: " + ${sourceParameter.name} );<#else>return <@writeTarget target=nullTarget.target/>;</#if>
    }

    <@includeModel object=resultType/> ${resultName};

    switch ( ${sourceParameter.name} ) {
    <#list valueMappings as valueMapping>
        case <@writeSource source=valueMapping.source/>: <#if valueMapping.targetAsException >throw new <@includeModel object=unexpectedValueMappingException />( "Unexpected enum constant: " + ${sourceParameter.name} );<#else>${resultName} = <@writeTarget target=valueMapping.target/>;
        break;</#if>
    </#list>
    default: <#if defaultTarget.targetAsException >throw new <@includeModel object=unexpectedValueMappingException />( "Unexpected enum constant: " + ${sourceParameter.name} )<#else>${resultName} = <@writeTarget target=defaultTarget.target/></#if>;
    }
    <#list beforeMappingReferencesWithMappingTarget as callback>
        <#if callback_index = 0>

        </#if>
        <@includeModel object=callback targetBeanName=resultName targetType=resultType/>
    </#list>
    <#list afterMappingReferences as callback>
        <#if callback_index = 0>

        </#if>
        <@includeModel object=callback targetBeanName=resultName targetType=resultType/>
    </#list>

    <#if !(valueMappings.empty && defaultTarget.targetAsException)>
    return ${resultName};
    </#if>
}
<#macro writeSource source="">
    <#if sourceParameter.type.enumType>
        ${source}<#t>
    <#elseif sourceParameter.type.string>
        "${source}"<#t>
    </#if>
</#macro>
<#macro writeTarget target="">
    <#if target?has_content>
        <#if returnType.enumType>
            <@includeModel object=returnType/>.${target}<#t>
        <#elseif returnType.string>
            "${target}"<#t>
        </#if>
    <#else>
        null<#t>
    </#if>
</#macro>
<#macro throws>
    <#if (thrownTypes?size > 0)><#lt> throws </#if><@compress single_line=true>
        <#list thrownTypes as exceptionType>
            <@includeModel object=exceptionType/>
            <#if exceptionType_has_next>, </#if><#t>
        </#list>
    </@compress>
</#macro>
