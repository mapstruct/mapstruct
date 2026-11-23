<#--

    Copyright MapStruct Authors.

    Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0

-->
<#-- @ftlvariable name="" type="org.mapstruct.ap.internal.model.common.Type" -->
<#-- typeAnnotation holds the annotation of the outermost type first, followed by the ones of each component type.
     For an array the outermost type is the first dimension and the innermost component is the leaf type. -->
<@compress single_line=true>
    <#assign typeAnnotation=ext.typeAnnotation![] />
    <#if hasExtendsBound()>
        ? extends <@includeModel object=typeBound />
    <#elseif hasSuperBound()>
        ? super <@includeModel object=typeBound />
    <#else>
        <#assign createReferenceName = createReferenceName() />
        <#assign createNoneArrayReferenceNames = createReferenceName?split("[]")/>
        <#assign createNoneArrayReferenceNamesSize = createNoneArrayReferenceNames?size -1>
        <#list createNoneArrayReferenceNames as createNoneArrayReferenceName>
            <#if createNoneArrayReferenceName?is_first>
                ${createNoneArrayReferenceName?contains(".")?then(createNoneArrayReferenceName?keep_before_last(".") + ".", "")}<#if typeAnnotation?has_content && typeAnnotation[createNoneArrayReferenceNamesSize]??>@<@includeModel object=typeAnnotation[createNoneArrayReferenceNamesSize] /> </#if>${createNoneArrayReferenceName?contains(".")?then(createNoneArrayReferenceName?keep_after_last("."), createNoneArrayReferenceName)}<#if (!ext.raw?? && typeParameters?size > 0) ><<#list typeParameters as typeParameter><@includeModel object=typeParameter /><#if typeParameter_has_next>, </#if></#list>></#if><#t>
            <#else>
                <#if typeAnnotation?has_content && typeAnnotation[createNoneArrayReferenceName?index - 1]??> @<@includeModel object=typeAnnotation[createNoneArrayReferenceName?index - 1] /> </#if><#if createNoneArrayReferenceName?is_last && ext.asVarArgs!false>...<#else>[]</#if><#t>
            </#if>
        </#list>
    </#if>
</@compress>
