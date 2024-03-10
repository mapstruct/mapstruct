<#--

    Copyright MapStruct Authors.

    Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0

-->
<#-- @ftlvariable name="" type="org.mapstruct.ap.internal.model.MapKeyMappingConstructorFragment" -->
this.${field.variableName} = new java.util.HashMap<>();
<#list keyMappings as entry>
  this.${field.variableName}.put( "${entry.source}", "${entry.target}" );
</#list>
