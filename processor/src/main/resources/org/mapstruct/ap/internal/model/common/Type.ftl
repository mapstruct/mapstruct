<#--

    Copyright MapStruct Authors.

    Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0

-->
<#-- @ftlvariable name="" type="org.mapstruct.ap.internal.model.common.Type" -->
<@compress single_line=true>
    <#assign typeAnnotation=ext.typeAnnotation![] />
    <#if hasExtendsBound()>
        ? extends <@includeModel object=typeBound />
    <#elseif hasSuperBound()>
        ? super <@includeModel object=typeBound />
    <#elseif isArrayType()>
        <@includeModel object=componentType raw=ext.raw typeAnnotation=removeFirstType(typeAnnotation)/><#if typeAnnotation?has_content && typeAnnotation[0]??> @<@includeModel object=typeAnnotation[0] /> </#if><#if ext.asVarArgs!false>...<#else>[]</#if>
    <#else>
        <#assign createReferenceName = createReferenceName() />
        ${createReferenceName?contains(".")?then(createReferenceName?keep_before_last(".") + ".", "")}<#if typeAnnotation?has_content && typeAnnotation[0]??>@<@includeModel object=typeAnnotation[0] /> </#if>${createReferenceName?contains(".")?then(createReferenceName?keep_after_last("."), createReferenceName)}<#if (!ext.raw?? && typeParameters?size > 0) ><<#list typeParameters as typeParameter><@includeModel object=typeParameter /><#if typeParameter_has_next>, </#if></#list>></#if>
    </#if>
</@compress>