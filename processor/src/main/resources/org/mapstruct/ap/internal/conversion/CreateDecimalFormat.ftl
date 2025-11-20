<#--

    Copyright MapStruct Authors.

    Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0

-->
<#-- @ftlvariable name="" type="org.mapstruct.ap.internal.model.SupportingMappingMethod" -->
private DecimalFormat ${name}( <#list parameters as param><@includeModel object=param/><#if param_has_next>, </#if></#list> ) {

    DecimalFormat df = new DecimalFormat( numberFormat<#if parameters.size() &gt; 1>, DecimalFormatSymbols.getInstance( locale )</#if> );
    df.setParseBigDecimal( true );
    return df;
}
