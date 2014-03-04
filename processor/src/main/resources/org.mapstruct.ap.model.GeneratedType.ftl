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
package ${packageName};

<#list importTypes as importedType>
import ${importedType.fullyQualifiedName};
</#list>

@Generated(
    value = "org.mapstruct.ap.MappingProcessor"<#if suppressGeneratorTimestamp == false>,
    date = "${.now?string("yyyy-MM-dd'T'HH:mm:ssZ")}"</#if>
)
<#list annotations as annotation>
<#nt><@includeModel object=annotation/>
</#list>
<#lt>${accessibility.keyword} class ${name}<#if superClassName??> extends ${superClassName}</#if><#if interfaceName??> implements ${interfaceName}</#if> {
<#list fields as field>

<#nt>    <@includeModel object=field/>
</#list>
<#list methods as method>

<#nt>    <@includeModel object=method/>
</#list>
}
