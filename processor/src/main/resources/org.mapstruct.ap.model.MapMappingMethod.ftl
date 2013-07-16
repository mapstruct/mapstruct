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
    public <@includeModel object=returnType /> ${name}(<#list parameters as param><@includeModel object=param.type/> ${param.name}<#if param_has_next>, </#if></#list>) {
        if ( ${sourceParameters[0].name} == null ) {
            return<#if returnType.name != "void"> null</#if>;
        }

        <#if existingInstanceMapping>
        ${resultName}.clear();
        <#else>
        <@includeModel object=resultType /> ${resultName} = new <#if resultType.mapImplementationType??><@includeModel object=resultType.mapImplementationType /><#else><@includeModel object=resultType /></#if>();
        </#if>

        for ( Map.Entry<<#list sourceParameters[0].type.typeParameters as typeParameter><@includeModel object=typeParameter /><#if typeParameter_has_next>, </#if></#list>> entry : ${sourceParameters[0].name}.entrySet() ) {

        <#-- key -->
        <#if keyMappingMethod??>
            <@includeModel object=resultType.typeParameters[0]/> key = <@includeModel object=keyMappingMethod input="entry.getKey()"/>;
        <#elseif keyConversion??>
            <#if (keyConversion.exceptionTypes?size == 0) >
            <@includeModel object=resultType.typeParameters[0]/> key = <@includeModel object=keyConversion/>;
            <#else>
            <@includeModel object=resultType.typeParameters[0]/> key;
            try {
                key = <@includeModel object=keyConversion/>;
            }
                <#list keyConversion.exceptionTypes as exceptionType>
            catch( ${exceptionType.name} e ) {
                throw new RuntimeException( e );
            }
                </#list>
            </#if>
        <#else>
            <@includeModel object=resultType.typeParameters[0]/> key = entry.getKey();
        </#if>
        <#-- value -->
        <#if valueMappingMethod??>
            <@includeModel object=resultType.typeParameters[1]/> value = <@includeModel object=valueMappingMethod input="entry.getValue()"/>;
        <#elseif valueConversion??>
            <#if (valueConversion.exceptionTypes?size == 0) >
            <@includeModel object=resultType.typeParameters[1]/> value = <@includeModel object=valueConversion/>;
            <#else>
            <@includeModel object=resultType.typeParameters[1]/> value;
            try {
                value = <@includeModel object=valueConversion/>;
            }
                <#list valueConversion.exceptionTypes as exceptionType>
            catch( ${exceptionType.name} e ) {
                throw new RuntimeException( e );
            }
                </#list>
            </#if>
        <#else>
            <@includeModel object=resultType.typeParameters[1]/> value = entry.getValue();
        </#if>

            ${resultName}.put( key, value );
        }
        <#if returnType.name != "void">

        return ${resultName};
        </#if>
    }
