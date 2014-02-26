<#--

     Copyright 2012-2014 Gunnar Morling (http://www.gunnarmorling.de/)
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
<#lt>${accessibility.keyword} <@includeModel object=returnType/> ${name}(<#list parameters as param><@includeModel object=param/><#if param_has_next>, </#if></#list>) {
    if ( <#list sourceParameters as sourceParam>${sourceParam.name} == null<#if sourceParam_has_next> && </#if></#list> ) {
        return<#if returnType.name != "void"> null</#if>;
    }

    <#if !existingInstanceMapping><@includeModel object=resultType/> ${resultName} = <#if factoryMethod??><@includeModel object=factoryMethod/><#else>new <@includeModel object=resultType/>()</#if>;</#if>
    <#if (sourceParameters?size > 1)>
        <#list sourceParameters as sourceParam>
    if ( ${sourceParam.name} != null ) {
            <#list propertyMappingsByParameter[sourceParam.name] as propertyMapping>
                <@includeModel object=propertyMapping targetBeanName=resultName/>
            </#list>
    }
        </#list>
    <#else>
        <#list propertyMappingsByParameter[sourceParameters[0].name] as propertyMapping>
            <@includeModel object=propertyMapping targetBeanName=resultName/>
        </#list>
    </#if>
    <#if returnType.name != "void">

    return ${resultName};
    </#if>
}