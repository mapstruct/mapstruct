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
    <#else>
        <#assign createReferenceName = createReferenceName() />
        <#assign createNoneArrayReferenceNames = createReferenceName?split("[]")/>
        <#assign createNoneArrayReferenceNamesSize = createNoneArrayReferenceNames?size -1>
        <#list createNoneArrayReferenceNames as createNoneArrayReferenceName>
            <#if createNoneArrayReferenceName?is_first>
                ${createNoneArrayReferenceName?contains(".")?then(createNoneArrayReferenceName?keep_before_last(".") + ".", "")}<#if typeAnnotation?has_content && typeAnnotation[minValue(createNoneArrayReferenceNamesSize, 1)]??>@<@includeModel object=typeAnnotation[minValue(createNoneArrayReferenceNamesSize, 1)] /> </#if>${createNoneArrayReferenceName?contains(".")?then(createNoneArrayReferenceName?keep_after_last("."), createNoneArrayReferenceName)}<#if (!ext.raw?? && typeParameters?size > 0) ><<#list typeParameters as typeParameter><@includeModel object=typeParameter /><#if typeParameter_has_next>, </#if></#list>></#if><#t>
            <#else>
                <#if typeAnnotation?has_content && typeAnnotation[arrayTypeAnnoationPosition(createNoneArrayReferenceName?index, createNoneArrayReferenceNamesSize)]??> @<@includeModel object=typeAnnotation[arrayTypeAnnoationPosition(createNoneArrayReferenceName?index, createNoneArrayReferenceNamesSize)] /> </#if><#if createNoneArrayReferenceName?is_last && ext.asVarArgs!false>...<#else>[]</#if><#t>
            </#if>
        </#list>
    </#if>
</@compress>
<#function minValue x y>
    <#if x < y>
        <#return x>
    <#else>
        <#return y>
    </#if>
</#function>
<#function arrayTypeAnnoationPosition pos size>
    <#if pos = 1>
        <#return 0>
    <#else>
        <#return size - pos + 2>
    </#if>
</#function>