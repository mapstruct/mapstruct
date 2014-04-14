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
<#lt>${accessibility.keyword} <@includeModel object=returnType /> ${name}(<#list parameters as param><@includeModel object=param/><#if param_has_next>, </#if></#list>)<@throws/> {
    <#list beforeMappingReferencesWithoutMappingTarget as callback>
    	<@includeModel object=callback targetBeanName=resultName targetType=resultType/>
    	<#if !callback_has_next>

    	</#if>
    </#list>
    if ( ${sourceParameter.name} == null ) {
        <#if !mapNullToDefault>
            return<#if returnType.name != "void"> null</#if>;
        <#else>
            <#if existingInstanceMapping>
                 ${resultName}.clear();
                 return<#if returnType.name != "void"> ${resultName}</#if>;
            <#else>
                 return <@returnObjectCreation/>;
            </#if>
        </#if>
    }

    <#if existingInstanceMapping>
        ${resultName}.clear();
    <#else>
        <@includeModel object=resultType /> ${resultName} = <@returnObjectCreation/>;
    </#if>

    <#list beforeMappingReferencesWithMappingTarget as callback>
        <@includeModel object=callback targetBeanName=resultName targetType=resultType/>
        <#if !callback_has_next>

        </#if>
    </#list>
    <#-- Once #148 has been addressed, the simple name of Map.Entry can be used -->
    for ( java.util.Map.Entry<<#list sourceParameter.type.typeParameters as typeParameter><@includeModel object=typeParameter /><#if typeParameter_has_next>, </#if></#list>> ${entryVariableName} : ${sourceParameter.name}.entrySet() ) {
    <#-- key -->
        <@includeModel object=keyAssignment
                   targetWriteAccessorName=keyVariableName
                   targetType=resultType.typeParameters[0].typeBound/>
    <#-- value -->
        <@includeModel object=valueAssignment
                   targetWriteAccessorName=valueVariableName
                   targetType=resultType.typeParameters[1].typeBound/>
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
            <#if exceptionType_has_next>, </#if>
        </#list>
    </@compress>
</#macro>
<#macro returnObjectCreation>
    <@compress single_line=true>
        <#if factoryMethod??>
             <@includeModel object=factoryMethod targetType=resultType/>
        <#else>
             new
             <#if resultType.implementationType??>
                  <@includeModel object=resultType.implementationType />
             <#else>
                  <@includeModel object=resultType />
             </#if>()
        </#if>
    </@compress>
</#macro>
