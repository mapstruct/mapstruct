<#--

     Copyright 2012 Gunnar Morling (http://www.gunnarmorling.de/)

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

public class ${implementationType} implements ${interfaceType} {

    <#list mapperMethods as oneMethod>
    public ${oneMethod.returnType.name} ${oneMethod.name}(${oneMethod.parameter.type.name} ${oneMethod.parameter.name}) {

        ${oneMethod.returnType.name} convertedObject = new ${oneMethod.returnType.name}();

        <#list oneMethod.bindings?values as oneBinding>
        <#if oneBinding.converterType??>
        convertedObject.set${oneBinding.targetProperty?cap_first}( new ${oneBinding.converterType.name}().from( ${oneMethod.parameter.name}.get${oneBinding.sourceProperty?cap_first}() ) );
        <#else>
        convertedObject.set${oneBinding.targetProperty?cap_first}( ${oneMethod.parameter.name}.get${oneBinding.sourceProperty?cap_first}() );
        </#if>
        </#list>

        return convertedObject;
    }
    </#list>
}
