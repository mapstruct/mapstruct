<#--

    Copyright MapStruct Authors.

    Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0

-->
<#-- @ftlvariable name="" type="org.mapstruct.ap.internal.model.SupportingConstructorFragment" -->
${definingMethod.supportingField.variableName} = <@includeModel object=definingMethod.supportingField.type/>.ofPattern( "${definingMethod.templateParameter['dateFormat']}" );