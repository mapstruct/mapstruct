 <#--

    Copyright MapStruct Authors.

    Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0

-->
 <#-- @ftlvariable name="" type="org.mapstruct.ap.internal.model.NoArgumentConstructor" -->
public ${name}() {
 <#list fragments as fragment>
     <#nt><@includeModel object=fragment/>
 </#list>
 }
