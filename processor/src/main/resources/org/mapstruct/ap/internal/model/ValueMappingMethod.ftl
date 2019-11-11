<#--

    Copyright MapStruct Authors.

    Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0

-->
<#-- @ftlvariable name="" type="org.mapstruct.ap.internal.model.ValueMappingMethod" -->
<#if overridden>@Override</#if>
<#lt>${accessibility.keyword} <@includeModel object=returnType/> ${name}(<#list parameters as param><@includeModel object=param/><#if param_has_next>, </#if></#list>) {
    <#list beforeMappingReferencesWithoutMappingTarget as callback>
        <@includeModel object=callback targetBeanName=resultName targetType=resultType/>
        <#if !callback_has_next>

        </#if>
    </#list>
    if ( ${sourceParameter.name} == null ) {
        return <@writeTarget target=nullTarget/>;
    }

    <@includeModel object=resultType/> ${resultName};

    switch ( ${sourceParameter.name} ) {
    <#list valueMappings as valueMapping>
        case <@writeSource source=valueMapping.source/>: ${resultName} = <@writeTarget target=valueMapping.target/>;
        break;
    </#list>
    default: <#if throwIllegalArgumentException>throw new IllegalArgumentException( "Unexpected enum constant: " + ${sourceParameter.name} )<#else>${resultName} = <@writeTarget target=defaultTarget/></#if>;
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

    <#if !(valueMappings.empty && throwIllegalArgumentException)>
    return ${resultName};
    </#if>
}
<#macro writeSource source="">
    <@compress single_line=true>
        <#if sourceParameter.type.enumType>
             ${source}
        <#elseif sourceParameter.type.string>
            "${source}"
        </#if>
    </@compress>
</#macro>
<#macro writeTarget target="">
    <@compress single_line=true>
        <#if target?has_content>
            <#if returnType.enumType>
                <@includeModel object=returnType/>.${target}
            <#elseif returnType.string>
                "${target}"
            </#if>
        <#else>
            null
        </#if>
    </@compress>
</#macro>