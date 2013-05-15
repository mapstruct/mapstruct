<#-- @ftlvariable name="" type="org.mapstruct.ap.model.Mapper" -->
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
package ${packageName};

<#list importedTypes as importedType>
import ${importedType.fullyQualifiedName};
</#list>

@Generated(
    value = "org.mapstruct.ap.MappingProcessor"<#if options.suppressGeneratorTimestamp == false>,
    date = "${.now?string("yyyy-MM-dd'T'HH:mm:ssZ")}"</#if>
)
public class ${implementationName} implements ${interfaceName} {

<#list usedMapperTypes as mapperType>
    private final ${mapperType.name} ${mapperType.name?uncap_first} = new ${mapperType.name}();
</#list>

<#list beanMappings as beanMapping>
    <#if beanMapping.mappingMethod.generationRequired == true>
        <#if beanMapping.iterableMapping == true>
    @Override
    public ${beanMapping.targetType.name}<${beanMapping.targetType.elementType.name}> ${beanMapping.mappingMethod.name}(${beanMapping.sourceType.name}<${beanMapping.sourceType.elementType.name}> ${beanMapping.mappingMethod.parameterName}) {
        if( ${beanMapping.mappingMethod.parameterName} == null ) {
            return null;
        }

        <#-- Use the interface type on the left side, except it is java.lang.Iterable; use the implementation type - if present - on the right side -->
        <#if beanMapping.targetType.name == "Iterable" && beanMapping.targetType.packageName == "java.lang">${beanMapping.targetType.iterableImplementationType.name}<#else>${beanMapping.targetType.name}</#if><${beanMapping.targetType.elementType.name}> ${beanMapping.targetType.name?uncap_first} = new <#if beanMapping.targetType.iterableImplementationType??>${beanMapping.targetType.iterableImplementationType.name}<#else>${beanMapping.targetType.name}</#if><${beanMapping.targetType.elementType.name}>();

        for ( ${beanMapping.sourceType.elementType.name} ${beanMapping.sourceType.elementType.name?uncap_first} : ${beanMapping.mappingMethod.parameterName} ) {
            <#if beanMapping.toConversion??>
            ${beanMapping.targetType.name?uncap_first}.add( ${beanMapping.toConversion} );
            <#else>
            ${beanMapping.targetType.name?uncap_first}.add( ${beanMapping.mappingMethod.elementMappingMethod.name}( ${beanMapping.sourceType.elementType.name?uncap_first} ) );
            </#if>
        }

        return ${beanMapping.targetType.name?uncap_first};
    }
        <#else>
    @Override
    public ${beanMapping.targetType.name} ${beanMapping.mappingMethod.name}(${beanMapping.sourceType.name} ${beanMapping.mappingMethod.parameterName}) {
        if( ${beanMapping.mappingMethod.parameterName} == null ) {
            return null;
        }

        ${beanMapping.targetType.name} ${beanMapping.targetType.name?uncap_first} = new ${beanMapping.targetType.name}();

            <#list beanMapping.propertyMappings as propertyMapping>
                <@simpleMap
                    targetBeanName=beanMapping.targetType.name?uncap_first
                    beanMappingMappingMethod=beanMapping.mappingMethod
                    conversion=propertyMapping.toConversion
                    mappingMethod=propertyMapping.mappingMethod!""
                    sourceName=propertyMapping.sourceName
                    targetPropertyName=propertyMapping.targetName?cap_first
                    targetType=propertyMapping.targetType />
            </#list>

        return ${beanMapping.targetType.name?uncap_first};
    }
        </#if>
    </#if>

    <#if beanMapping.reverseMappingMethod??>
        <#if beanMapping.reverseMappingMethod.generationRequired == true>
            <#if beanMapping.iterableMapping == true>

    @Override
    public ${beanMapping.sourceType.name}<${beanMapping.sourceType.elementType.name}> ${beanMapping.reverseMappingMethod.name}(${beanMapping.targetType.name}<${beanMapping.targetType.elementType.name}> ${beanMapping.reverseMappingMethod.parameterName}) {
        if( ${beanMapping.reverseMappingMethod.parameterName} == null ) {
            return null;
        }

        <#-- Use the interface type on the left side, except it is java.lang.Iterable; use the implementation type - if present - on the right side -->
        <#if beanMapping.sourceType.name == "Iterable" && beanMapping.sourceType.packageName == "java.lang">${beanMapping.sourceType.iterableImplementationType.name}<#else>${beanMapping.sourceType.name}</#if><${beanMapping.sourceType.elementType.name}> ${beanMapping.sourceType.name?uncap_first} = new <#if beanMapping.sourceType.iterableImplementationType??>${beanMapping.sourceType.iterableImplementationType.name}<#else>${beanMapping.sourceType.name}</#if><${beanMapping.sourceType.elementType.name}>();

        for ( ${beanMapping.targetType.elementType.name} ${beanMapping.targetType.elementType.name?uncap_first} : ${beanMapping.reverseMappingMethod.parameterName} ) {
            <#if beanMapping.fromConversion??>
            ${beanMapping.sourceType.name?uncap_first}.add( ${beanMapping.fromConversion} );
            <#else>
            ${beanMapping.sourceType.name?uncap_first}.add( ${beanMapping.reverseMappingMethod.elementMappingMethod.name}( ${beanMapping.targetType.elementType.name?uncap_first} ) );
            </#if>
        }

        return ${beanMapping.sourceType.name?uncap_first};
    }
            <#else>
    @Override
    public ${beanMapping.sourceType.name} ${beanMapping.reverseMappingMethod.name}(${beanMapping.targetType.name} ${beanMapping.reverseMappingMethod.parameterName}) {
        if( ${beanMapping.reverseMappingMethod.parameterName} == null ) {
            return null;
        }

        ${beanMapping.sourceType.name} ${beanMapping.sourceType.name?uncap_first} = new ${beanMapping.sourceType.name}();

                <#list beanMapping.propertyMappings as propertyMapping>
                    <@simpleMap
                        targetBeanName=beanMapping.sourceType.name?uncap_first
                        beanMappingMappingMethod=beanMapping.reverseMappingMethod
                        conversion=propertyMapping.fromConversion
                        mappingMethod=propertyMapping.reverseMappingMethod!""
                        sourceName=propertyMapping.targetName
                        targetPropertyName=propertyMapping.sourceName?cap_first
                        targetType=propertyMapping.sourceType />
                </#list>

        return ${beanMapping.sourceType.name?uncap_first};
    }
            </#if>
        </#if>
    </#if>
</#list>
}
<#macro simpleMap targetType sourceName targetBeanName targetPropertyName beanMappingMappingMethod mappingMethod conversion="">
        <#if conversion != "">
            <#if targetType.primitive == true>
        if( ${beanMappingMappingMethod.parameterName}.get${sourceName?cap_first}() != null ) {
            ${targetBeanName}.set${targetPropertyName}( ${conversion} );
        }
            <#else>
        ${targetBeanName}.set${targetPropertyName}( ${conversion} );
            </#if>
        <#-- invoke mapping method -->
        <#elseif mappingMethod != "">
        ${targetBeanName}.set${targetPropertyName}( <#if mappingMethod.declaringMapper??>${mappingMethod.declaringMapper.name?uncap_first}.</#if>${mappingMethod.name}( ${beanMappingMappingMethod.parameterName}.get${sourceName?cap_first}() ) );
        <#else>
            <#if targetType.collectionType == true>
        if ( ${beanMappingMappingMethod.parameterName}.get${sourceName?cap_first}() != null ) {
            ${targetBeanName}.set${targetPropertyName}( new <#if targetType.collectionImplementationType??>${targetType.collectionImplementationType.name}<#else>${targetType.name}</#if><#if targetType.elementType??><${targetType.elementType.name}></#if>( ${beanMappingMappingMethod.parameterName}.get${sourceName?cap_first}() ) );
        }
            <#else>
        ${targetBeanName}.set${targetPropertyName}( ${beanMappingMappingMethod.parameterName}.get${sourceName?cap_first}() );
            </#if>
        </#if>
</#macro>