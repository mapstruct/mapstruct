<#--

    Copyright MapStruct Authors.

    Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0

-->
private final <@includeModel object=type/> ${variableName} = <#if annotatedMapper>Mappers.getMapper( <@includeModel object=type/>.class );<#else>new <@includeModel object=type/>();</#if>