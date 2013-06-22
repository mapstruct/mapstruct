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
    public ${targetType.name} ${name}(${sourceType.name} ${parameterName}) {
        if ( ${parameterName} == null ) {
            return null;
        }

        ${targetType.name} ${targetType.name?uncap_first} = new ${targetType.name}();

            <#list propertyMappings as propertyMapping>
                <@simpleMap
                    sourceBeanName=parameterName
                    sourceType=propertyMapping.sourceType
                    sourceAccessorName=propertyMapping.sourceReadAccessorName
                    targetBeanName=targetType.name?uncap_first
                    targetType=propertyMapping.targetType
                    targetAccessorName=propertyMapping.targetWriteAccessorName
                    conversion=propertyMapping.toConversion
                    mappingMethod=propertyMapping.mappingMethod
                />
            </#list>

        return ${targetType.name?uncap_first};
    }

<#-- Generates the mapping of one bean property -->
<#macro simpleMap sourceBeanName sourceType sourceAccessorName targetBeanName targetType targetAccessorName conversion="" mappingMethod="">
        <#-- a) invoke mapping method -->
        <#if mappingMethod != "">
        ${targetBeanName}.${targetAccessorName}( <#if mappingMethod.declaringMapper??>${mappingMethod.declaringMapper.name?uncap_first}.</#if>${mappingMethod.name}( ${sourceBeanName}.${sourceAccessorName}() ) );
        <#-- b) simple conversion -->
        <#elseif conversion != "">
            <#if sourceType.primitive == false>
        if ( ${sourceBeanName}.${sourceAccessorName}() != null ) {
            ${targetBeanName}.${targetAccessorName}( ${conversion} );
        }
            <#else>
        ${targetBeanName}.${targetAccessorName}( ${conversion} );
            </#if>
        <#-- c) simply set -->
        <#else>
            <#if targetType.collectionType == true>
        if ( ${sourceBeanName}.${sourceAccessorName}() != null ) {
            ${targetBeanName}.${targetAccessorName}( new <#if targetType.collectionImplementationType??>${targetType.collectionImplementationType.name}<#else>${targetType.name}</#if><#if targetType.elementType??><${targetType.elementType.name}></#if>( ${sourceBeanName}.${sourceAccessorName}() ) );
        }
            <#else>
        ${targetBeanName}.${targetAccessorName}( ${sourceBeanName}.${sourceAccessorName}() );
            </#if>
        </#if>
</#macro>
