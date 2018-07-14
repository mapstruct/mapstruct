<#--

    Copyright MapStruct Authors.

    Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0

-->
private <@includeModel object=findType("Calendar")/> ${name}(<@includeModel object=findType("ZonedDateTime")/> dateTime) {
    if ( dateTime == null ) {
       return null;
    }

    <@includeModel object=findType("Calendar")/> instance = <@includeModel object=findType("Calendar")/>.getInstance( TimeZone.getTimeZone( dateTime.getZone() ) );
    instance.setTimeInMillis( dateTime.toInstant().toEpochMilli() );
    return instance;
}
