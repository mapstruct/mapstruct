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
    public <@includeModel object=targetType/> ${name}(<@includeModel object=sourceType/> ${parameterName}) {
        if ( ${parameterName} == null ) {
            return null;
        }

        <#-- Use the interface type on the left side, except it is java.lang.Iterable; use the implementation type - if present - on the right side -->
        <#if targetType.name == "Iterable" && targetType.packageName == "java.lang">${targetType.iterableImplementationType.name}<#else>${targetType.name}</#if><<@includeModel object=targetType.typeParameters[0]/>> ${returnValueName} = new <#if targetType.iterableImplementationType??>${targetType.iterableImplementationType.name}<#else>${targetType.name}</#if><<@includeModel object=targetType.typeParameters[0]/>>();

        for ( <@includeModel object=sourceType.typeParameters[0]/> ${loopVariableName} : ${parameterName} ) {
            <#if elementMappingMethod??>
            ${returnValueName}.add( <@includeModel object=elementMappingMethod input="${loopVariableName}"/> );
            <#else>
                <#if (conversion.exceptionTypes?size == 0) >
            ${returnValueName}.add( <@includeModel object=conversion/> );
                <#else>
            try {
                ${returnValueName}.add( <@includeModel object=conversion/> );
            }
                    <#list conversion.exceptionTypes as exceptionType>
            catch( ${exceptionType.name} e ) {
                throw new RuntimeException( e );
            }
                    </#list>
                </#if>
            </#if>
        }

        return ${returnValueName};
    }
