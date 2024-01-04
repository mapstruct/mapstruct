<#--

    Copyright MapStruct Authors.

    Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0

-->
<#-- @ftlvariable name="" type="org.mapstruct.ap.internal.model.StreamMappingMethod" -->
<#list annotations as annotation>
    <#nt><@includeModel object=annotation/>
</#list>
<#if overridden>@Override</#if>
<#lt>${accessibility.keyword} <@includeModel object=returnType/> ${name}(<#list parameters as param><@includeModel object=param/><#if param_has_next>, </#if></#list>)<@throws/> {
    <#--TODO does it even make sense to do a callback if the result is a Stream, as they are immutable-->
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
            <#elseif resultType.iterableType>
                <#if existingInstanceMapping>
                    ${resultName}.clear();
                    return<#if returnType.name != "void"> ${resultName}</#if>;
                <#else>
                    return <@includeModel object=iterableCreation useSizeIfPossible=false/>;
                </#if>
            <#else>
                <#if existingInstanceMapping>
                    <#-- We cannot update an existing stream so we just return the old one -->
                    return<#if returnType.name != "void"> ${resultName}</#if>;
                <#else>
                    return Stream.empty();
                </#if>
            </#if>
        </#if>
    }

    <#-- A variable needs to be defined if there are before or after mappings and this is not exisitingInstanceMapping -->
    <#assign needVarDefine = (beforeMappingReferencesWithMappingTarget?has_content || afterMappingReferences?has_content) && !existingInstanceMapping />

    <#if resultType.arrayType>
        <#if needVarDefine>
            <#assign needVarDefine = false />
            <#-- We create a null array which later will be directly assigned from the stream-->
            <@includeModel object=resultElementType/>[] ${resultName} = null;
        </#if>
    <#elseif resultType.iterableType>
        <#if existingInstanceMapping>
            ${resultName}.clear();
        <#elseif needVarDefine>
            <#assign needVarDefine = false />
            <#-- Use the interface type on the left side, except it is java.lang.Iterable; use the implementation type - if present - on the right side -->
            <@iterableLocalVarDef/> ${resultName} = <@includeModel object=iterableCreation useSizeIfPossible=true/>;
        </#if>
    <#else>
        <#-- Streams are immutable so we can't update them -->
        <#if needVarDefine>
            <#assign needVarDefine = false />
            <@iterableLocalVarDef/> ${resultName} = Stream.empty();
        </#if>
    </#if>

    <#list beforeMappingReferencesWithMappingTarget as callback>
        <@includeModel object=callback targetBeanName=resultName targetType=resultType/>
        <#if !callback_has_next>

        </#if>
    </#list>

    <#-- If there are no after mappings, no variable was created before i.e. no before mappings
        and this is not an existingInstanceMapping then we can return immediatelly -->
    <#assign canReturnImmediatelly = !afterMappingReferences?has_content && !needVarDefine && !existingInstanceMapping/>

    <#if resultType.arrayType>
        <#if existingInstanceMapping>
        int ${index1Name} = 0;
        for ( <@includeModel object=resultElementType/> ${loopVariableName} : ${sourceParameter.name}.limit( ${resultName}.length )<@streamMapSupplier />.toArray( ${resultElementType}[]::new ) ) {
            if ( ( ${index1Name} >= ${resultName}.length ) ) {
                break;
            }
            ${resultName}[${index1Name}++] = ${loopVariableName};
        }
        <#else>
            <#if canReturnImmediatelly><#if returnType.name != "void">return </#if><#else> <#if needVarDefine>${resultElementType}[] <#else>${resultName} = </#if></#if>${sourceParameter.name}<@streamMapSupplier />
                        .toArray( <@includeModel object=resultElementType/>[]::new );
        </#if>
    <#elseif resultType.iterableType>
        <#if existingInstanceMapping || !canReturnImmediatelly>
            ${resultName}.addAll( ${sourceParameter.name}<@streamMapSupplier />
                                    .collect( Collectors.toCollection( <@iterableCollectionSupplier /> ) )
                                );
        <#else>
            <@returnLocalVarDefOrUpdate>
                <#lt>${sourceParameter.name}<@streamMapSupplier />
                    .collect( Collectors.toCollection( <@iterableCollectionSupplier /> ) );
            </@returnLocalVarDefOrUpdate>

        </#if>
    <#else>
        <#-- Streams are immutable so we can't update them -->
        <#if !existingInstanceMapping>
            <#--TODO fhr: after the result is no longer the same instance, how does it affect the
                Before mapping methods. Does it even make sense to have before mapping on a stream? -->
            <#if sourceParameter.type.arrayType>
                <@returnLocalVarDefOrUpdate>Stream.of( ${sourceParameter.name} )<@streamMapSupplier />;</@returnLocalVarDefOrUpdate>
            <#elseif sourceParameter.type.collectionType>
                <@returnLocalVarDefOrUpdate>${sourceParameter.name}.stream()<@streamMapSupplier />;</@returnLocalVarDefOrUpdate>
            <#elseif sourceParameter.type.iterableType>
                <@returnLocalVarDefOrUpdate>StreamSupport.stream( ${sourceParameter.name}.spliterator(), false )<@streamMapSupplier />;</@returnLocalVarDefOrUpdate>
            <#else>
                <@returnLocalVarDefOrUpdate>${sourceParameter.name}<@streamMapSupplier />;</@returnLocalVarDefOrUpdate>
            </#if>
        </#if>

    </#if>

    <#list afterMappingReferences as callback>
    	<#if callback_index = 0>

    	</#if>
    	<@includeModel object=callback targetBeanName=resultName targetType=resultType/>
    </#list>

    <#if !canReturnImmediatelly && returnType.name != "void">
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
<#macro iterableCollectionSupplier>
    <@compress single_line=true>
        <#if resultType.implementationType??>
            <@includeModel object=resultType.implementationType/>
        <#else>
            <@includeModel object=resultType/></#if>::new
    </@compress>
</#macro>
<#macro streamMapSupplier>
    <@compress>
        <#if !elementAssignment.directAssignment?? || !elementAssignment.directAssignment>
            .map( <@includeModel object=elementAssignment targetBeanName=resultName targetType=resultElementType/> )
        </#if>
    </@compress>
</#macro>
<#macro returnLocalVarDefOrUpdate>
    <#if canReturnImmediatelly><#if returnType.name != "void">return </#if><#elseif needVarDefine><@iterableLocalVarDef/> ${resultName} = <#else>${resultName} = </#if><#nested />
</#macro>
