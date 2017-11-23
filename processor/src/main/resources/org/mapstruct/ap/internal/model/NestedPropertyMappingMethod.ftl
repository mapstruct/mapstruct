<#-- @ftlvariable name="" type="org.mapstruct.ap.internal.model.NestedPropertyMappingMethod" -->
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
<#lt>private <@includeModel object=returnType/> ${name}(<#list parameters as param><@includeModel object=param/><#if param_has_next>, </#if></#list>)<@throws/> {
    if ( ${sourceParameter.name} == null ) {
        return ${returnType.null};
    }
<#list propertyEntries as entry>
    <#if entry.presenceCheckerName?? >
    if ( !<@localVarName index=entry_index/>.${entry.presenceCheckerName}() ) {
        return ${returnType.null};
    }
    </#if>
    <@includeModel object=entry.type/> ${entry.name} = <@localVarName index=entry_index/>.${entry.accessorName};
    <#if !entry.presenceCheckerName?? >
    <#if !entry.type.primitive>
    if ( ${entry.name} == null ) {
        return ${returnType.null};
    }
    </#if>
    </#if>
    <#if !entry_has_next>
    return ${entry.name};
    </#if>
</#list>
}
<#macro localVarName index><#if index == 0>${sourceParameter.name}<#else>${propertyEntries[index-1].name}</#if></#macro>
<#macro throws>
    <#if (thrownTypes?size > 0)><#lt> throws </#if><@compress single_line=true>
        <#list thrownTypes as exceptionType>
            <@includeModel object=exceptionType/>
            <#if exceptionType_has_next>, </#if><#t>
        </#list>
    </@compress>
</#macro>