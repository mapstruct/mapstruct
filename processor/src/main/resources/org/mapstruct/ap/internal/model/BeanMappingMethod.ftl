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
@Override
<#lt>${accessibility.keyword} <@includeModel object=returnType/> ${name}(<#list parameters as param><@includeModel object=param/><#if param_has_next>, </#if></#list>)<@throws/> {
    <#list beforeMappingReferencesWithoutMappingTarget as callback>
    	<@includeModel object=callback targetBeanName=resultName targetType=resultType/>
    	<#if !callback_has_next>

    	</#if>
    </#list>
    <#if !mapNullToDefault>
    if ( <#list sourceParametersExcludingPrimitives as sourceParam>${sourceParam.name} == null<#if sourceParam_has_next> && </#if></#list> ) {
        return<#if returnType.name != "void"> null</#if>;
    }
    </#if>

    <#if !existingInstanceMapping>
        <@includeModel object=resultType/> ${resultName} = <#if factoryMethod??><@includeModel object=factoryMethod targetType=resultType/><#else>new <@includeModel object=resultType/>()</#if>;

    </#if>
    <#list beforeMappingReferencesWithMappingTarget as callback>
    	<@includeModel object=callback targetBeanName=resultName targetType=resultType/>
    	<#if !callback_has_next>

    	</#if>
    </#list>
    <#if (sourceParameters?size > 1)>
        <#list sourceParametersExcludingPrimitives as sourceParam>
            <#if (propertyMappingsByParameter[sourceParam.name]?size > 0)>
                if ( ${sourceParam.name} != null ) {
                    <#list propertyMappingsByParameter[sourceParam.name] as propertyMapping>
                        <@includeModel object=propertyMapping targetBeanName=resultName existingInstanceMapping=existingInstanceMapping defaultValueAssignment=propertyMapping.defaultValueAssignment/>
                    </#list>
                }
            </#if>
        </#list>
        <#list sourcePrimitiveParameters as sourceParam>
            <#if (propertyMappingsByParameter[sourceParam.name]?size > 0)>
                <#list propertyMappingsByParameter[sourceParam.name] as propertyMapping>
                    <@includeModel object=propertyMapping targetBeanName=resultName existingInstanceMapping=existingInstanceMapping defaultValueAssignment=propertyMapping.defaultValueAssignment/>
                </#list>
            </#if>
        </#list>
    <#else>
        <#if mapNullToDefault>if ( ${sourceParameters[0].name} != null ) {</#if>
        <#list propertyMappingsByParameter[sourceParameters[0].name] as propertyMapping>
            <@includeModel object=propertyMapping targetBeanName=resultName existingInstanceMapping=existingInstanceMapping defaultValueAssignment=propertyMapping.defaultValueAssignment/>
        </#list>
        <#if mapNullToDefault>}</#if>
    </#if>
    <#list constantMappings as constantMapping>
         <@includeModel object=constantMapping targetBeanName=resultName existingInstanceMapping=existingInstanceMapping/>
    </#list>
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