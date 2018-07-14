<#--

    Copyright MapStruct Authors.

    Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0

-->
<@compress single_line=true>
    <#if factoryMethod??>
        <@includeModel object=factoryMethod targetType=resultType/>
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