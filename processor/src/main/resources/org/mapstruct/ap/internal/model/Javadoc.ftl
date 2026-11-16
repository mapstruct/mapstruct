<#--

    Copyright MapStruct Authors.

    Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0

-->
<#-- @ftlvariable name="" type="org.mapstruct.ap.internal.model.Javadoc" -->
/**
<#list value?split("\n") as line><#nt>*<#if line?has_content> </#if>${line?trim}
</#list>
<#if !authors.isEmpty()>
*
<#list authors as author> <#nt>* @author ${author?trim}
</#list>
</#if>
<#if deprecated?has_content>
*
<#nt>* @deprecated ${deprecated?trim}
</#if>
<#if since?has_content>
*
<#nt>* @since ${since?trim}
</#if>
<#nt> */