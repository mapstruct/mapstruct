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
<@compress single_line=true>
    <#-- method is either internal to the mapper class, or external (via uses) declaringMapper!=null -->
    <#if declaringMapper??>${mapperVariableName}.</#if>${name}( <@arguments/> )
    <#macro arguments>
        <#list parameters as param>
            <#if param.targetType>
                <#-- a class is passed on for casting, see @TargetType -->
                ${ext.targetType}.class
            <#else>
                <#if methodRefChild??>
                    <#-- the nested case: another method -->
                    <@includeModel object=methodRefChild source=ext.source targetType=singleSourceParameterType.name/>
                <#elseif typeConversion??>
                    <#-- the nested case: a type conversion -->
                    <@includeModel object=typeConversion source=ext.source targetType=singleSourceParameterType.name/>
                <#else>
                    <#-- the non nested case -->
                    ${ext.source}
                </#if>
            </#if>
            <#if param_has_next>, </#if>
        </#list>
        <#-- context parameter, e.g. for buildin methods concerning date conversion -->
        <#if contextParam??>, ${contextParam}</#if>
    </#macro>
</@compress>
