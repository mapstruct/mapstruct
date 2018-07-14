<#--

    Copyright MapStruct Authors.

    Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0

-->
<#if publicEmptyConstructor>
public ${name}() {
}
</#if>

<#list annotations as annotation>
    <#nt><@includeModel object=annotation/>
</#list>
public ${name}(<#list mapperReferences as mapperReference><#list mapperReference.annotations as annotation><@includeModel object=annotation/> </#list><@includeModel object=mapperReference.type/> ${mapperReference.variableName}<#if mapperReference_has_next>, </#if></#list>) {
    <#list mapperReferences as mapperReference>
        this.${mapperReference.variableName} = ${mapperReference.variableName};
    </#list>
}