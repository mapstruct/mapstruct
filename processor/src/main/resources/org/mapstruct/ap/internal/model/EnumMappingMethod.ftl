<#--

    Copyright MapStruct Authors.

    Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0

-->
<#-- @ftlvariable name="" type="org.mapstruct.ap.internal.model.EnumMappingMethod" -->
@Override
public <@includeModel object=returnType/> ${name}(<#list parameters as param><@includeModel object=param/><#if param_has_next>, </#if></#list>) {
    <#list beforeMappingReferencesWithoutMappingTarget as callback>
        <@includeModel object=callback targetBeanName=resultName targetType=resultType/>
        <#if !callback_has_next>

        </#if>
    </#list>
    if ( ${sourceParameter.name} == null ) {
        return  null;
    }

    <@includeModel object=resultType/> ${resultName};

    switch ( ${sourceParameter.name} ) {
    <#list enumMappings as enumMapping>
        case ${enumMapping.source}: ${resultName} = <@includeModel object=returnType/>.${enumMapping.target};
            break;
    </#list>
    default: throw new IllegalArgumentException( "Unexpected enum constant: " + ${sourceParameter.name} );
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
