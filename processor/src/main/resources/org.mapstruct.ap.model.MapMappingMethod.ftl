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
    public <@includeModel object=targetType /> ${name}(<@includeModel object=sourceType /> ${parameterName}) {
        if ( ${parameterName} == null ) {
            return null;
        }

        <@includeModel object=targetType /> ${returnValueName} = new <#if targetType.mapImplementationType??><@includeModel object=targetType.mapImplementationType /><#else><@includeModel object=targetType /></#if>();

        for ( Map.Entry<<#list sourceType.typeParameters as typeParameter><@includeModel object=typeParameter /><#if typeParameter_has_next>, </#if></#list>> entry : ${parameterName}.entrySet() ) {

        <#-- key -->
        <#if keyMappingMethod??>
            <@includeModel object=targetType.typeParameters[0]/> key = <@includeModel object=keyMappingMethod input="entry.getKey()"/>;
        <#elseif keyConversion??>
            <#if (keyConversion.exceptionTypes?size == 0) >
            <@includeModel object=targetType.typeParameters[0]/> key = <@includeModel object=keyConversion/>;
            <#else>
            <@includeModel object=targetType.typeParameters[0]/> key;
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
            <@includeModel object=targetType.typeParameters[0]/> key = entry.getKey();
        </#if>
        <#-- value -->
        <#if valueMappingMethod??>
            <@includeModel object=targetType.typeParameters[1]/> value = <@includeModel object=valueMappingMethod input="entry.getValue()"/>;
        <#elseif valueConversion??>
            <#if (valueConversion.exceptionTypes?size == 0) >
            <@includeModel object=targetType.typeParameters[1]/> value = <@includeModel object=valueConversion/>;
            <#else>
            <@includeModel object=targetType.typeParameters[1]/> value;
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
            <@includeModel object=targetType.typeParameters[1]/> value = entry.getValue();
        </#if>

            ${returnValueName}.put( key, value );
        }

        return ${returnValueName};
    }
