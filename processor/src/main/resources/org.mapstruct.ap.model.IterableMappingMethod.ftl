<#--

     Copyright 2012-2015 Gunnar Morling (http://www.gunnarmorling.de/)
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
            <#else>
                <#if existingInstanceMapping>
                    ${resultName}.clear();
                    return<#if returnType.name != "void"> ${resultName}</#if>;
                <#else>
                    return <@iterableCreation/>;
                </#if>
            </#if>
        </#if>
    }

    <#if resultType.arrayType>
        <#if !existingInstanceMapping>
            <@includeModel object=resultElementType/>[] ${resultName} = new <@includeModel object=resultElementType/>[<@iterableSize/>];
        </#if>
    <#else>
        <#if existingInstanceMapping>
            ${resultName}.clear();
        <#else>
            <#-- Use the interface type on the left side, except it is java.lang.Iterable; use the implementation type - if present - on the right side -->
            <@iterableLocalVarDef/> ${resultName} = <@iterableCreation/>;
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
            <#if exceptionType_has_next>, </#if>
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
                <@includeModel object=resultType/>
            </#if>()
        </#if>
    </@compress>
</#macro>
