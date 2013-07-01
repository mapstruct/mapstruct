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
    public ${targetType.name}<${targetType.elementType.name}> ${name}(${sourceType.name}<${sourceType.elementType.name}> ${parameterName}) {
        if ( ${parameterName} == null ) {
            return null;
        }

        <#-- Use the interface type on the left side, except it is java.lang.Iterable; use the implementation type - if present - on the right side -->
        <#if targetType.name == "Iterable" && targetType.packageName == "java.lang">${targetType.iterableImplementationType.name}<#else>${targetType.name}</#if><${targetType.elementType.name}> ${targetType.name?uncap_first} = new <#if targetType.iterableImplementationType??>${targetType.iterableImplementationType.name}<#else>${targetType.name}</#if><${targetType.elementType.name}>();

        for ( ${sourceType.elementType.name} ${sourceType.elementType.name?uncap_first} : ${parameterName} ) {
            <#if toConversion??>
            ${targetType.name?uncap_first}.add( ${toConversion} );
            <#else>
            ${targetType.name?uncap_first}.add( ${elementMappingMethod.name}( ${sourceType.elementType.name?uncap_first} ) );
            </#if>
        }

        return ${targetType.name?uncap_first};
    }
