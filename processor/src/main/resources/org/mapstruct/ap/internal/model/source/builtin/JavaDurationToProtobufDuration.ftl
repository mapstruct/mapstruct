<#--

    Copyright MapStruct Authors.

    Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0

-->
<#-- @ftlvariable name="" type="org.mapstruct.ap.internal.model.SupportingMappingMethod" -->
private static <@includeModel object=findType("com.google.protobuf.Duration")/> ${name}( <@includeModel object=findType("java.time.Duration")/> duration ) {
    if ( duration == null ) {
        return null;
    }

    <@includeModel object=findType("com.google.protobuf.Duration")/>.Builder builder = <@includeModel object=findType("com.google.protobuf.Duration")/>.newBuilder();
    builder.setSeconds( duration.getSeconds() );
    builder.setNanos( duration.getNano() );
    return builder.build();
}
