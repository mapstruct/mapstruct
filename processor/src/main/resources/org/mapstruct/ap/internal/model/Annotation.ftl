<#--

    Copyright MapStruct Authors.

    Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0

-->
<#-- @ftlvariable name="" type="org.mapstruct.ap.internal.model.Annotation" -->
<#switch properties?size>
    <#case 0>
        @<@includeModel object=type/><#rt>
    <#break>
    <#case 1>
        @<@includeModel object=type/>(<@includeModel object=properties[0]/>)<#rt>
    <#break>
    <#default>
        @<@includeModel object=type/>(
            <#list properties as property>
                <#nt><@includeModel object=property/><#if property_has_next>,</#if>
            </#list>
        )<#rt>
</#switch>