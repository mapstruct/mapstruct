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
<#if ( ext.isLocalVar?? && ext.isLocalVar==true )>
    <#-- assignment is a local variable assignment -->
    <#if (exceptionTypes?size == 0) >
        ${ext.targetType} ${ext.target} = <@assignment/>;
    <#else>
        ${ext.targetType} ${ext.target};
        try {
            ${ext.target} = <@assignment/>;
        }
        <#list exceptionTypes as exceptionType>
        catch ( <@includeModel object=exceptionType/> e ) {
           throw new RuntimeException( e );
        }
        </#list>
    </#if>
<#else>
    <#-- assignment is a method call -->
    <#if (exceptionTypes?size == 0) >
        ${ext.target}( <@assignment/> );
    <#else>
        try {
            ${ext.target}( <@assignment/> );
        }
        <#list exceptionTypes as exceptionType>
        catch ( <@includeModel object=exceptionType/> e ) {
           throw new RuntimeException( e );
        }
        </#list>
    </#if>
</#if>
<#macro assignment>
    <#compress>
    <#switch assignmentType>
        <#case "TYPE_CONVERSION">
            <@includeModel object=typeConversion source="${ext.source}" targetType=ext.targetType/>
            <#break>
        <#case "METHOD_REFERENCE">
            <@includeModel object=methodReference source="${ext.source}" targetType=ext.targetType raw=ext.raw/>
            <#break>
        <#case "ASSIGNMENT">
            ${ext.source}
            <#break>
    </#switch>
    </#compress>
</#macro>
