<#--

    Copyright MapStruct Authors.

    Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0

-->
<#-- @ftlvariable name="" type="org.mapstruct.ap.internal.model.presence.AllPresenceChecksPresenceCheck" -->
<@compress single_line=true>
<#list presenceChecks as presenceCheck>
    <#if presenceCheck_index != 0>
        &&
    </#if>
    <@includeModel object=presenceCheck />
</#list>
</@compress>