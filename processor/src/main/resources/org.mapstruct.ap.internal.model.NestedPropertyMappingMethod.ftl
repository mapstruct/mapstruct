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
<#lt>private <@includeModel object=returnType/> ${name}(<#list parameters as param><@includeModel object=param/><#if param_has_next>, </#if></#list>) {

    if ( ${sourceParameter.name} == null ) {
        return ${returnType.null};
    }
    <#list propertyEntries as entry>
    <@includeModel object=entry.type/> ${entry.name} = <#if entry_index == 0>${sourceParameter.name}.${entry.accessorName}()<#else>${propertyEntries[entry_index-1].name}.${entry.accessorName}()</#if>;
    <#if !entry.type.primitive>
    if ( ${entry.name} == null ) {
        return ${returnType.null};
    }
    </#if>
    <#if !entry_has_next>
    return ${entry.name};
    </#if>
    </#list>
}
