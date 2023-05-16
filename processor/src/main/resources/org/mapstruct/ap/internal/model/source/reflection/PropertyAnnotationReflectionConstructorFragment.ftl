<#--

    Copyright MapStruct Authors.

    Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0

-->
<#-- @ftlvariable name="" type="org.mapstruct.ap.internal.model.SupportingConstructorFragment" -->
try {
    <#list fragment.fieldInitializers as initializer>
        <#nt><@includeModel object=initializer/>
    </#list>
}
catch (ReflectiveOperationException e) {
    throw new RuntimeException(e);
}
