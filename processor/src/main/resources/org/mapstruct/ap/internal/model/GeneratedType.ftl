<#--

    Copyright MapStruct Authors.

    Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0

-->
<#-- @ftlvariable name="" type="org.mapstruct.ap.internal.model.GeneratedType" -->
<#if hasPackageName()>
package ${packageName};
</#if>

<#list importTypeNames as importedType>
import ${importedType};
</#list>

<#if javadoc??><#nt><@includeModel object=javadoc/></#if>
<#if !generatedTypeAvailable>/*</#if>
@Generated(
    value = "org.mapstruct.ap.MappingProcessor"<#if suppressGeneratorTimestamp == false>,
    date = "${.now?string("yyyy-MM-dd'T'HH:mm:ssZ")}"</#if><#if suppressGeneratorVersionComment == false>,
    comments = "version: ${versionInformation.mapStructVersion}, compiler: ${versionInformation.compiler}, environment: Java ${versionInformation.runtimeVersion} (${versionInformation.runtimeVendor})"</#if>
)<#if !generatedTypeAvailable>
*/</#if>
<#list annotations as annotation>
<#nt><@includeModel object=annotation/>
</#list>
<#lt>${accessibility.keyword} class ${name} <#if mapperDefinitionType.interface>implements<#else>extends</#if> <@includeModel object=mapperDefinitionType/> {

<#list fields as field><#if field.used><#nt>    <@includeModel object=field/>
</#if></#list>

<#if constructor??><#nt>    <@includeModel object=constructor/></#if>

<#list methods as method>
<#nt>    <@includeModel object=method/>
</#list>
}
