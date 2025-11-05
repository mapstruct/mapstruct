<#--

    Copyright MapStruct Authors.

    Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0

-->
<#-- @ftlvariable name="" type="org.mapstruct.ap.internal.model.SupportingMappingMethod" -->
private static <@includeModel object=findType("com.google.protobuf.Timestamp")/> ${name}( <@includeModel object=findType("java.time.Instant")/> instant ) {
    if ( instant == null ) {
        return null;
    }

    <@includeModel object=findType("com.google.protobuf.Timestamp")/>.Builder builder = <@includeModel object=findType("com.google.protobuf.Timestamp")/>.newBuilder();
    builder.setSeconds( instant.getEpochSecond() );
    builder.setNanos( instant.getNano() );
    return builder.build();
}
