<#--

    Copyright MapStruct Authors.

    Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0

-->
<#-- @ftlvariable name="" type="org.mapstruct.ap.internal.model.common.Type" -->
<@compress single_line=true>
    <#if hasExtendsBound()>
        ? extends <@includeModel object=typeBound />
    <#elseif hasSuperBound()>
        ? super <@includeModel object=typeBound />
    <#else>
        <#assign createReferenceName = createReferenceName() />
        <#assign createNoneArrayReferenceName = createReferenceName?keep_before("[]")/>
        ${createNoneArrayReferenceName?contains(".")?then(createNoneArrayReferenceName?keep_before_last(".") + ".", "")}<#if ext.typeAnnotation??>@<@includeModel object=ext.typeAnnotation /> </#if>${createNoneArrayReferenceName?contains(".")?then(createNoneArrayReferenceName?keep_after_last("."), createNoneArrayReferenceName)}<#if (!ext.raw?? && typeParameters?size > 0) ><<#list typeParameters as typeParameter><@includeModel object=typeParameter /><#if typeParameter_has_next>, </#if></#list>></#if>${createReferenceName?keep_after("[]")}<#if ext.asVarArgs!false>...<#elseif isArrayType()>[]</#if>
    </#if>
</@compress>