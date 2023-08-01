<#--

    Copyright MapStruct Authors.

    Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0

-->
<#-- @ftlvariable name="" type="org.mapstruct.ap.internal.model.IterableMappingMethod" -->
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
    if ( <@includeModel object=sourceParameterPresenceCheck.negate() /> ) {
        <#if !mapNullToDefault>
            return<#if returnType.name != "void"> <#if existingInstanceMapping>${resultName}<#else>null</#if></#if>;
        <#else>
            <#if resultType.arrayType>
                <#if existingInstanceMapping>
                    <#-- we can't clear an existing array, so we've got to clear by setting values to default -->
                    for (int ${index2Name} = 0; ${index2Name} < ${resultName}.length; ${index2Name}++ ) {
                        ${resultName}[${index2Name}] = ${resultElementType.null};
                    }
                    return<#if returnType.name != "void"> ${resultName}</#if>;
                <#else>
                    return new <@includeModel object=resultElementType/>[0];
                </#if>
            <#else>
                <#if existingInstanceMapping>
                    ${resultName}.clear();
                    return<#if returnType.name != "void"> ${resultName}</#if>;
                <#else>
                    return <@includeModel object=iterableCreation useSizeIfPossible=false/>;
                </#if>
            </#if>
        </#if>
    }

    <#if resultType.arrayType>
        <#if !existingInstanceMapping>
            <#assign elementTypeString><@includeModel object=resultElementType/></#assign>
            ${elementTypeString}[] ${resultName} = new ${elementTypeString?keep_before('[]')}[<@iterableSize/>]${elementTypeString?replace('[^\\[\\]]+', '', 'r')};
        </#if>
    <#else>
        <#if existingInstanceMapping>
            ${resultName}.clear();
        <#else>
            <#-- Use the interface type on the left side, except it is java.lang.Iterable; use the implementation type - if present - on the right side -->
            <@iterableLocalVarDef/> ${resultName} = <@includeModel object=iterableCreation useSizeIfPossible=true/>;
        </#if>
    </#if>
    <#list beforeMappingReferencesWithMappingTarget as callback>
    	<@includeModel object=callback targetBeanName=resultName targetType=resultType/>
    	<#if !callback_has_next>

    	</#if>
    </#list>
    <#if resultType.arrayType>
        int ${index1Name} = 0;
        for ( <@includeModel object=sourceElementType/> ${loopVariableName} : ${sourceParameter.name} ) {
            <#if existingInstanceMapping>
            if ( ( ${index1Name} >= ${resultName}.length ) || ( ${index1Name} >= <@iterableSize/> ) ) {
                break;
            }
            </#if>
            <@includeModel object=elementAssignment targetWriteAccessorName=resultName+"[${index1Name}]" targetType=resultElementType isTargetDefined=true/>
            ${index1Name}++;
        }
    <#else>
        for ( <@includeModel object=sourceElementType/> ${loopVariableName} : ${sourceParameter.name} ) {
            <@includeModel object=elementAssignment targetBeanName=resultName targetWriteAccessorName="add" targetType=resultElementType/>
        }
    </#if>
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
<#macro iterableSize>
    <@compress single_line=true>
        <#if sourceParameter.type.arrayType>
           ${sourceParameter.name}.length
        <#else>
           ${sourceParameter.name}.size()
        </#if>
    </@compress>
</#macro>
<#macro iterableLocalVarDef>
    <@compress single_line=true>
        <#if resultType.fullyQualifiedName == "java.lang.Iterable">
            <@includeModel object=resultType.implementationType/>
        <#else>
            <@includeModel object=resultType/>
        </#if>
    </@compress>
</#macro>