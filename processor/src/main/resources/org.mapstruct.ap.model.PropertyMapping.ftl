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
<#-- a) invoke mapping method -->
<#if mappingMethod??>
<#if hasTargetSetter>
${ext.targetBeanName}.${targetAccessorName}( <@includeModel object=mappingMethod input="${sourceBeanName}.${sourceAccessorName}()"/> );
<#else>
if ( ${sourceBeanName}.${sourceAccessorName}() != null ) {
${ext.targetBeanName}.${targetAccessorName}().addAll( <@includeModel object=mappingMethod input="${sourceBeanName}.${sourceAccessorName}()"/> );
}
</#if>
<#-- b) simple conversion -->
<#elseif conversion??>
    <#if sourceType.primitive == false>
if ( ${sourceBeanName}.${sourceAccessorName}() != null ) {
     <@applyConversion targetBeanName=ext.targetBeanName targetAccessorName=targetAccessorName conversion=conversion/>
}
    <#else>
<@applyConversion targetBeanName=ext.targetBeanName targetAccessorName=targetAccessorName conversion=conversion/>
    </#if>
<#-- c) simply set -->
<#else>
    <#if targetType.collectionType || targetType.mapType>
if ( ${sourceBeanName}.${sourceAccessorName}() != null ) {
<#if hasTargetSetter>
    ${ext.targetBeanName}.${targetAccessorName}( new <#if targetType.implementationType??><@includeModel object=targetType.implementationType/><#else><@includeModel object=targetType/></#if>( ${sourceBeanName}.${sourceAccessorName}() ) );
<#else>
    ${ext.targetBeanName}.${targetAccessorName}().addAll( new <#if targetType.implementationType??><@includeModel object=targetType.implementationType/><#else><@includeModel object=targetType/></#if>( ${sourceBeanName}.${sourceAccessorName}() ) );
</#if>
}
    <#else>
${ext.targetBeanName}.${targetAccessorName}( ${sourceBeanName}.${sourceAccessorName}() );
    </#if>
</#if>
<#macro applyConversion targetBeanName targetAccessorName conversion>
    <#if (conversion.exceptionTypes?size == 0) >
        ${targetBeanName}.${targetAccessorName}( <@includeModel object=conversion/> );
    <#else>
        try {
            ${targetBeanName}.${targetAccessorName}( <@includeModel object=conversion/> );
        }
        <#list conversion.exceptionTypes as exceptionType>
        catch( ${exceptionType.name} e ) {
            throw new RuntimeException( e );
        }
        </#list>
    </#if>
</#macro>