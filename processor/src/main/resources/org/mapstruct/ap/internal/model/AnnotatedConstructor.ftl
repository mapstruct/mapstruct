 <#--

    Copyright MapStruct Authors.

    Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0

-->
 <#-- @ftlvariable name="" type="org.mapstruct.ap.internal.model.AnnotatedConstructor" -->
 <#if noArgumentConstructor??>
     <@includeModel object=noArgumentConstructor/>
</#if>

<#list annotations as annotation>
    <#nt><@includeModel object=annotation/>
</#list>
public ${name}(<#list mapperReferences as mapperReference><#list mapperReference.annotations as annotation><@includeModel object=annotation/> </#list><@includeModel object=mapperReference.type/> ${mapperReference.variableName}<#if mapperReference_has_next>, </#if></#list>) {
    <#if noArgumentConstructor?? && !noArgumentConstructor.fragments.empty>this();</#if>
 <#assign superMappers = []/>
 <#list mapperReferences as mapperReference>
     <#if mapperReference.isNeededForSuper()>
         <#assign superMappers = superMappers + [mapperReference.variableName] />
     </#if>
 </#list>

 <#if superMappers?size gt 0>
     super( ${superMappers?join(", ")} );
 </#if>
    <#list mapperReferences as mapperReference>
        <#if !mapperReference.isNeededForSuper()>
        this.${mapperReference.variableName} = ${mapperReference.variableName};
        </#if>
    </#list>
     <#list fragments as fragment>
         <#nt><@includeModel object=fragment/>
    </#list>
}