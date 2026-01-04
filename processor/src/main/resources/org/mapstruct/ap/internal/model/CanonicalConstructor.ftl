<#--

    Copyright MapStruct Authors.

    Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0

-->
<#-- @ftlvariable name="" type="org.mapstruct.ap.internal.model.CanonicalConstructor" -->
<#list annotations as annotation>
    <#nt><@includeModel object=annotation/>
</#list>
public ${name}(<#list parameters as param><@includeModel object=param.type/> ${param.name}<#if param_has_next>, </#if></#list>) {
    super( <#list superParameters as param>${param.name}<#if param_has_next>, </#if></#list> );
    <#list classParameters as param>
        this.${param.name} = ${param.name};
    </#list>
}

<#if shouldIncludeNoArgConstructor()>
public ${name}() {
    super( <#list superParameters as param><#if param.annotatedMapper>Mappers.getMapper( <@includeModel object=param.type/>.class )<#else>new <@includeModel object=param.type/>()</#if><#if param_has_next>, </#if></#list> );
    <#list classParameters as param>
        this.${param.name} = <#if param.annotatedMapper>Mappers.getMapper( <@includeModel object=param.type/>.class )<#else>new <@includeModel object=param.type/>()</#if>;
    </#list>
}
</#if>
