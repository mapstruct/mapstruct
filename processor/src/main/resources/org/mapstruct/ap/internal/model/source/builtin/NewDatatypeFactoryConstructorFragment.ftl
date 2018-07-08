<#--

    Copyright MapStruct Authors.

    Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0

-->
<#-- @ftlvariable name="" type="org.mapstruct.ap.internal.model.SupportingConstructorFragment" -->
try {
     ${definingMethod.supportingField.variableName} = <@includeModel object=definingMethod.supportingField.type/>.newInstance();
}
catch ( <@includeModel object=definingMethod.findType("DatatypeConfigurationException")/> ex ) {
    throw new RuntimeException( ex );
}
