 <#--

    Copyright MapStruct Authors.

    Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0

-->
 <#-- @ftlvariable name="" type="org.mapstruct.ap.internal.model.AbstractClassConstructor" -->

<#list annotations as annotation>
    <#nt><@includeModel object=annotation/>
</#list>
public ${name}( <#list superclassParameters as mapperReference><@includeModel object=mapperReference.type/> ${mapperReference.variableName}<#if mapperReference_has_next>, </#if></#list><#list extraParameters as mapperReference>, <@includeModel object=mapperReference.type/> ${mapperReference.variableName}</#list> ) {
    super( <#list superclassParameters as mapperReference>${mapperReference.variableName}<#if mapperReference_has_next>, </#if></#list> );
    <#list extraParameters as mapperReference>
        this.${mapperReference.variableName} = ${mapperReference.variableName};
    </#list>
}