<#-- @ftlvariable name="" type="org.mapstruct.ap.internal.model.StreamMappingMethod" -->
<#--

     Copyright 2012-2016 Gunnar Morling (http://www.gunnarmorling.de/)
     and/or other contributors as indicated by the @authors tag. See the
     copyright.txt file in the distribution for a full listing of all
     contributors.

     Licensed under the Apache License, Version 2.0 (the "License");
     you may not use this file except in compliance with the License.
     You may obtain a copy of the License at

         http://www.apache.org/licenses/LICENSE-2.0

     Unless required by applicable law or agreed to in writing, software
     distributed under the License is distributed on an "AS IS" BASIS,
     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
     See the License for the specific language governing permissions and
     limitations under the License.

-->
<#if overridden>@Override</#if>
<#lt>${accessibility.keyword} <@includeModel object=returnType/> ${name}(<#list parameters as param><@includeModel object=param/><#if param_has_next>, </#if></#list>)<@throws/> {
    <#--TODO does it even make sense to do a callback if the result is a Stream, as they are immutable-->
    <#list beforeMappingReferencesWithoutMappingTarget as callback>
    	<@includeModel object=callback targetBeanName=resultName targetType=resultType/>
    	<#if !callback_has_next>

    	</#if>
    </#list>
    if ( ${sourceParameter.name} == null ) {
        <#if !mapNullToDefault>
            <#-- returned target type starts to miss-align here with target handed via param, TODO is this right? -->
            return<#if returnType.name != "void"> null</#if>;
        <#else>
            <#if resultType.arrayType>
                <#if existingInstanceMapping>
                    <#-- we can't clear an existing array, so we've got to clear by setting values to default -->
                    for (int ${index2Name} = 0; ${index2Name} < ${resultName}.length; ${index2Name}++ ) {
                        ${resultName}[${index2Name}] = ${defaultValue};
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
                    return <@iterableCreation/>;
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

    <#if resultType.arrayType>
        <#if !existingInstanceMapping>
            <#-- We create a null array which later will be directly assigned from the stream-->
            ${resultElementType}[] ${resultName} = null;
        </#if>
    <#elseif resultType.iterableType>
        <#if existingInstanceMapping>
            ${resultName}.clear();
        <#else>
            <#-- Use the interface type on the left side, except it is java.lang.Iterable; use the implementation type - if present - on the right side -->
            <@iterableLocalVarDef/> ${resultName} = <@iterableCreation/>;
        </#if>
    <#else>
        <#-- Streams are immutable so we can't update them -->
        <#if !existingInstanceMapping>
            <@iterableLocalVarDef/> ${resultName} = Stream.empty();
        </#if>
    </#if>

    <#list beforeMappingReferencesWithMappingTarget as callback>
        <@includeModel object=callback targetBeanName=resultName targetType=resultType/>
        <#if !callback_has_next>

        </#if>
    </#list>

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
        ${resultName} = ${sourceParameter.name}<@streamMapSupplier />
                        .toArray( <@includeModel object=resultElementType/>[]::new );
        </#if>
    <#elseif resultType.iterableType>
        ${resultName}.addAll( ${sourceParameter.name}<@streamMapSupplier />
                                .collect( Collectors.toCollection( <@iterableCollectionSupplier /> ) )
                            );
    <#else>
        <#-- Streams are immutable so we can't update them -->
        <#if !existingInstanceMapping>
            <#--TODO fhr: after the the result is no longer the same instance, how does it affect the
                Before mapping methods. Does it even make sense to have before mapping on a stream? -->
            <#if sourceParameter.type.arrayType>
                ${resultName} = Stream.of( ${sourceParameter.name} )<@streamMapSupplier />;
            <#elseif sourceParameter.type.collectionType>
                ${resultName} = ${sourceParameter.name}.stream()<@streamMapSupplier />;
            <#elseif sourceParameter.type.iterableType>
                ${resultName} = StreamSupport.stream( ${sourceParameter.name}.spliterator(), false )<@streamMapSupplier />;
            <#else>
                ${resultName} = ${sourceParameter.name}<@streamMapSupplier />;
            </#if>
        </#if>

    </#if>

    <#--TODO does it even make sense to do a callback if the result is a Stream, as they are immutable-->
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
<#macro iterableCreation>
    <@compress single_line=true>
        <#if factoryMethod??>
            <@includeModel object=factoryMethod targetType=resultType/>
        <#else>
            new
            <#if resultType.implementationType??>
                <@includeModel object=resultType.implementationType/>
            <#else>
                <@includeModel object=resultType/></#if>()
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
