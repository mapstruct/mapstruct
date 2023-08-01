<#--

    Copyright MapStruct Authors.

    Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0

-->
<#-- @ftlvariable name="" type="org.mapstruct.ap.internal.model.MapMappingMethod" -->
<#list annotations as annotation>
    <#nt><@includeModel object=annotation/>
</#list>
<#if overridden>@Override</#if>
<#lt>${accessibility.keyword} <@includeModel object=returnType /> ${name}(<#list parameters as param><@includeModel object=param/><#if param_has_next>, </#if></#list>)<@throws/> {
    <#list beforeMappingReferencesWithoutMappingTarget as callback>
    	<@includeModel object=callback targetBeanName=resultName targetType=resultType/>
    	<#if !callback_has_next>

    	</#if>
    </#list>
    if ( <@includeModel object=sourceParameterPresenceCheck.negate() /> ) {
        <#if !mapNullToDefault>
            return<#if returnType.name != "void"> <#if existingInstanceMapping>${resultName}<#else>null</#if></#if>;
        <#else>
            <#if existingInstanceMapping>
                 ${resultName}.clear();
                 return<#if returnType.name != "void"> ${resultName}</#if>;
            <#else>
                 return <@includeModel object=iterableCreation useSizeIfPossible=false/>;
            </#if>
        </#if>
    }

    <#if existingInstanceMapping>
        ${resultName}.clear();
    <#else>
        <@includeModel object=resultType /> ${resultName} = <@includeModel object=iterableCreation useSizeIfPossible=true/>;
    </#if>

    <#list beforeMappingReferencesWithMappingTarget as callback>
        <@includeModel object=callback targetBeanName=resultName targetType=resultType/>
        <#if !callback_has_next>

        </#if>
    </#list>
    <#-- Once #148 has been addressed, the simple name of Map.Entry can be used -->
    for ( java.util.Map.Entry<<#list sourceElementTypes as typeParameter><@includeModel object=typeParameter /><#if typeParameter_has_next>, </#if></#list>> ${entryVariableName} : ${sourceParameter.name}.entrySet() ) {
    <#-- key -->
        <@includeModel object=keyAssignment
                   targetWriteAccessorName=keyVariableName
                   targetType=resultElementTypes[0].typeBound/>
    <#-- value -->
        <@includeModel object=valueAssignment
                   targetWriteAccessorName=valueVariableName
                   targetType=resultElementTypes[1].typeBound/>
        ${resultName}.put( ${keyVariableName}, ${valueVariableName} );
    }
    <#list afterMappingReferences as callback>
    	<#if callback_index = 0>

    	</#if>
    	<@includeModel object=callback targetBeanName=resultName targetType=resultType/>
    </#list>
    <#if returnType.name != "void">

        return ${resultName};
    </#if>
}
<#macro throws>
    <#if (thrownTypes?size > 0)><#lt> throws </#if><@compress single_line=true>
        <#list thrownTypes as exceptionType>
            <@includeModel object=exceptionType/>
            <#if exceptionType_has_next>, </#if><#t>
        </#list>
    </@compress>
</#macro>