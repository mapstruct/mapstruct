<#--

    Copyright MapStruct Authors.

    Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0

-->
private <@includeModel object=findType("ZonedDateTime")/> ${name}(<@includeModel object=findType("Calendar")/> cal) {
    if ( cal == null ) {
        return null;
    }

    return <@includeModel object=findType("ZonedDateTime")/>.ofInstant( cal.toInstant(), cal.getTimeZone().toZoneId() );
}
