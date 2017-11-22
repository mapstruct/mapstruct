<#-- @ftlvariable name="" type="org.mapstruct.ap.internal.model.ValueMappingMethod" -->
<#--

     Copyright 2012-2017 Gunnar Morling (http://www.gunnarmorling.de/)
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
