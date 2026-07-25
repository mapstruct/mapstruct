<#--

    Copyright MapStruct Authors.

    Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0

-->
<#-- @ftlvariable name="" type="org.mapstruct.ap.internal.model.SupportingMappingMethod" -->
private static <@includeModel object=findType("java.time.Instant")/> ${name}( <@includeModel object=findType("com.google.protobuf.Timestamp")/> timestamp ) {
    if ( timestamp == null ) {
        return null;
    }

    return <@includeModel object=findType("java.time.Instant")/>.ofEpochSecond( timestamp.getSeconds(), timestamp.getNanos() );
}
