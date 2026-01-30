<#--

    Copyright MapStruct Authors.

    Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0

-->
<#-- @ftlvariable name="" type="org.mapstruct.ap.internal.model.presence.OptionalPresenceCheck" -->
<@compress single_line=true>
    <#if isNegate()>
        <#if versionInformation.isSourceVersionAtLeast11()>
            ${sourceReference}.isEmpty()
        <#else>
            !${sourceReference}.isPresent()
        </#if>
    <#else>
        ${sourceReference}.isPresent()
    </#if>
</@compress>