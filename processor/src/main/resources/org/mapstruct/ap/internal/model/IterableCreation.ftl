<#--

    Copyright MapStruct Authors.

    Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0

-->
<#-- @ftlvariable name="" type="org.mapstruct.ap.internal.model.IterableCreation" -->
<@compress single_line=true>
    <#if factoryMethod??>
        <@includeModel object=factoryMethod targetType=resultType/>
    <#elseif enumSet>
        EnumSet.noneOf( <@includeModel object=enumSetElementType raw=true/>.class )
    <#elseif sourceVersionAtLeast19 && resultType.implementationType?? && ext.useSizeIfPossible?? && ext.useSizeIfPossible && canUseSize>
        <#if linkedHashSet>
            LinkedHashSet.newLinkedHashSet( <@iterableSize /> )
        <#elseif linkedHashMap>
            LinkedHashMap.newLinkedHashMap( <@iterableSize /> )
        <#elseif hashSet>
            HashSet.newHashSet( <@iterableSize /> )
        <#elseif hashMap>
            HashMap.newHashMap( <@iterableSize /> )
        <#else>
            new <@includeModel object=resultType.implementationType/><#if ext.useSizeIfPossible?? && ext.useSizeIfPossible && canUseSize>( <@sizeForCreation /> )<#else>()</#if>
        </#if>
    <#else>
    new
        <#if resultType.implementationType??>
            <@includeModel object=resultType.implementationType/><#if ext.useSizeIfPossible?? && ext.useSizeIfPossible && canUseSize>( <@sizeForCreation /> )<#else>()</#if>
        <#else>
            <@includeModel object=resultType/>()</#if>
        </#if>
</@compress>
<#macro sizeForCreation>
    <@compress single_line=true>
        <#if loadFactorAdjustment>
            Math.max( (int) ( <@iterableSize/> / .75f ) + 1, 16 )
        <#else>
            <@iterableSize/>
        </#if>
    </@compress>
</#macro>
<#macro iterableSize>
    <@compress single_line=true>
        <#if sourceParameter.type.arrayType>
            ${sourceParameter.name}.length
        <#else>
            ${sourceParameter.name}.size()
        </#if>
    </@compress>
</#macro>