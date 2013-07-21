<#--

     Copyright 2012-2013 Gunnar Morling (http://www.gunnarmorling.de/)
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
    public ${returnType.name} ${name}(<#list parameters as param>${param.type.name} ${param.name}<#if param_has_next>, </#if></#list>) {
        if ( ${singleSourceParameter.name} == null ) {
            return<#if returnType.name != "void"> null</#if>;
        }
        <#if !existingInstanceMapping>
        ${resultType.name} ${resultName} = new ${resultType.name}();
        </#if>

        <#list propertyMappings as propertyMapping>
            <@includeModel object=propertyMapping/>
        </#list>
        <#if returnType.name != "void">

        return ${resultName};
        </#if>
    }
