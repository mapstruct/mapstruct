<#--

    Copyright MapStruct Authors.

    Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0

-->
<#if overridden>@Override</#if>
<#lt>${accessibility.keyword} <@includeModel object=returnType/> ${name}(<#list parameters as param><@includeModel object=param/><#if param_has_next>, </#if></#list>) {
    <#list beforeMappingReferencesWithoutMappingTarget as callback>
        <@includeModel object=callback targetBeanName=resultName targetType=resultType/>
        <#if !callback_has_next>

        </#if>
    </#list>
    if ( ${sourceParameter.name} == null ) {
        return <#if nullTarget??><@includeModel object=returnType/>.${nullTarget}<#else>null</#if>;
    }

    <@includeModel object=resultType/> ${resultName};

    switch ( ${sourceParameter.name} ) {
    <#list valueMappings as valueMapping>
        case ${valueMapping.source}: ${resultName} = <#if valueMapping.target??><@includeModel object=returnType/>.${valueMapping.target}<#else>null</#if>;
        break;
    </#list>
    default: <#if throwIllegalArgumentException>throw new IllegalArgumentException( "Unexpected enum constant: " + ${sourceParameter.name} )<#else>${resultName} = <#if defaultTarget??><@includeModel object=returnType/>.${defaultTarget}<#else>null</#if></#if>;
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

    return ${resultName};
}
