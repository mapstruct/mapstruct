<#--

    Copyright MapStruct Authors.

    Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0

-->
<#-- @ftlvariable name="" type="org.mapstruct.ap.internal.model.SupportingMappingMethod" -->
private static <@includeModel object=findType("com.google.type.TimeOfDay")/> ${name}( <@includeModel object=findType("java.time.LocalTime")/> timeOfDay ) {
    if ( timeOfDay == null ) {
        return null;
    }

    <@includeModel object=findType("com.google.type.TimeOfDay")/>.Builder builder = <@includeModel object=findType("com.google.type.TimeOfDay")/>.newBuilder();
    builder.setHours( timeOfDay.getHour() );
    builder.setMinutes( timeOfDay.getMinute() );
    builder.setSeconds( timeOfDay.getSecond() );
    builder.setNanos( timeOfDay.getNano() );
    return builder.build();
}
