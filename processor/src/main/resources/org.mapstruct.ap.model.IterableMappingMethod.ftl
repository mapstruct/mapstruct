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
    public <@includeModel object=returnType/> ${name}(<#list parameters as param><@includeModel object=param/><#if param_has_next>, </#if></#list>) {
        if ( ${sourceParameter.name} == null ) {
            return<#if returnType.name != "void"> null</#if>;
        }

        <#if existingInstanceMapping>
        ${resultName}.clear();
        <#else>
        <#-- Use the interface type on the left side, except it is java.lang.Iterable; use the implementation type - if present - on the right side -->
        <#if resultType.name == "Iterable" && resultType.packageName == "java.lang">${resultType.iterableImplementationType.name}<#else>${resultType.name}</#if><<@includeModel object=resultType.typeParameters[0]/>> ${resultName} = new <#if resultType.iterableImplementationType??>${resultType.iterableImplementationType.name}<#else>${resultType.name}</#if><<@includeModel object=resultType.typeParameters[0]/>>();
       </#if>

        for ( <@includeModel object=sourceParameter.type.typeParameters[0]/> ${loopVariableName} : ${sourceParameter.name} ) {
            <#if elementMappingMethod??>
            ${resultName}.add( <@includeModel object=elementMappingMethod input="${loopVariableName}"/> );
            <#else>
                <#if (conversion.exceptionTypes?size == 0) >
            ${resultName}.add( <@includeModel object=conversion/> );
                <#else>
            try {
                ${resultName}.add( <@includeModel object=conversion/> );
            }
                    <#list conversion.exceptionTypes as exceptionType>
            catch( ${exceptionType.name} e ) {
                throw new RuntimeException( e );
            }
                    </#list>
                </#if>
            </#if>
        }
        <#if returnType.name != "void">

        return ${resultName};
        </#if>
    }
