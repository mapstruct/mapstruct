<#--

     Copyright 2012-2013 Gunnar Morling (http://www.gunnarmorling.de/)

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
package ${packageName};

import java.util.ArrayList;
import java.util.List;

public class ${implementationName} implements ${interfaceName} {

    <#list beanMappings as beanMapping>
    <#if beanMapping.iterableMapping == true>
    @Override
    public ${beanMapping.targetType.name}<${beanMapping.targetType.elementType.name}> ${beanMapping.mappingMethod.name}(${beanMapping.sourceType.name}<${beanMapping.sourceType.elementType.name}> ${beanMapping.mappingMethod.parameterName}) {
        
        ${beanMapping.targetType.name}<${beanMapping.targetType.elementType.name}> ${beanMapping.targetType.name?uncap_first} = new ${beanMapping.targetType.name}<${beanMapping.targetType.elementType.name}>();
        
        for ( ${beanMapping.sourceType.elementType.name} ${beanMapping.sourceType.elementType.name?uncap_first} : ${beanMapping.mappingMethod.parameterName} ) {
            ${beanMapping.targetType.name?uncap_first}.add( ${beanMapping.mappingMethod.elementMappingMethod.name}( ${beanMapping.sourceType.elementType.name?uncap_first} ) );
        }
        
        return ${beanMapping.targetType.name?uncap_first};        
    }
    <#else>
    @Override
    public ${beanMapping.targetType.name} ${beanMapping.mappingMethod.name}(${beanMapping.sourceType.name} ${beanMapping.mappingMethod.parameterName}) {
        
        ${beanMapping.targetType.name} ${beanMapping.targetType.name?uncap_first} = new ${beanMapping.targetType.name}();

        <#list beanMapping.propertyMappings as propertyMapping>
        <#if propertyMapping.converterType??>
        ${beanMapping.targetType.name?uncap_first}.set${propertyMapping.targetName?cap_first}( new ${propertyMapping.converterType.name}().from( ${beanMapping.mappingMethod.parameterName}.get${propertyMapping.sourceName?cap_first}() ) );
        <#elseif propertyMapping.mappingMethod??>
        ${beanMapping.targetType.name?uncap_first}.set${propertyMapping.targetName?cap_first}( ${propertyMapping.mappingMethod.name}( ${beanMapping.mappingMethod.parameterName}.get${propertyMapping.sourceName?cap_first}() ) );
        <#else>
        ${beanMapping.targetType.name?uncap_first}.set${propertyMapping.targetName?cap_first}( ${beanMapping.mappingMethod.parameterName}.get${propertyMapping.sourceName?cap_first}() );
        </#if>
        </#list>

        return ${beanMapping.targetType.name?uncap_first};
    }
    </#if>

    <#if beanMapping.reverseMappingMethod??>
    
    <#if beanMapping.iterableMapping == true>
    @Override
    public ${beanMapping.sourceType.name}<${beanMapping.sourceType.elementType.name}> ${beanMapping.reverseMappingMethod.name}(${beanMapping.targetType.name}<${beanMapping.targetType.elementType.name}> ${beanMapping.reverseMappingMethod.parameterName}) {
        
        ${beanMapping.sourceType.name}<${beanMapping.sourceType.elementType.name}> ${beanMapping.sourceType.name?uncap_first} = new ${beanMapping.sourceType.name}<${beanMapping.sourceType.elementType.name}>();
        
        for ( ${beanMapping.targetType.elementType.name} ${beanMapping.targetType.elementType.name?uncap_first} : ${beanMapping.reverseMappingMethod.parameterName} ) {
            ${beanMapping.sourceType.name?uncap_first}.add( ${beanMapping.reverseMappingMethod.elementMappingMethod.name}( ${beanMapping.targetType.elementType.name?uncap_first} ) );
        }
        
        return ${beanMapping.sourceType.name?uncap_first};        
    }
    <#else>
    @Override
    public ${beanMapping.sourceType.name} ${beanMapping.reverseMappingMethod.name}(${beanMapping.targetType.name} ${beanMapping.reverseMappingMethod.parameterName}) {
        ${beanMapping.sourceType.name} ${beanMapping.sourceType.name?uncap_first} = new ${beanMapping.sourceType.name}();

        <#list beanMapping.propertyMappings as propertyMapping>
        <#if propertyMapping.converterType??>
        ${beanMapping.sourceType.name?uncap_first}.set${propertyMapping.sourceName?cap_first}( new ${propertyMapping.converterType.name}().to( ${beanMapping.reverseMappingMethod.parameterName}.get${propertyMapping.targetName?cap_first}() ) );
        <#elseif propertyMapping.reverseMappingMethod??>
        ${beanMapping.sourceType.name?uncap_first}.set${propertyMapping.sourceName?cap_first}( ${propertyMapping.reverseMappingMethod.name}( ${beanMapping.reverseMappingMethod.parameterName}.get${propertyMapping.targetName?cap_first}() ) );
        <#else>
        ${beanMapping.sourceType.name?uncap_first}.set${propertyMapping.sourceName?cap_first}( ${beanMapping.reverseMappingMethod.parameterName}.get${propertyMapping.targetName?cap_first}() );
        </#if>
        </#list>

        return ${beanMapping.sourceType.name?uncap_first};
    }    
    </#if>
    </#if>
    
    </#list>
}
