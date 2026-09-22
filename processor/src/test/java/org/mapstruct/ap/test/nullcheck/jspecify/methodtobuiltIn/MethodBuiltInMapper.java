/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nullcheck.jspecify.methodtobuiltIn;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.jspecify.annotations.Nullable;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface MethodBuiltInMapper {

    MethodBuiltInMapper INSTANCE = Mappers.getMapper( MethodBuiltInMapper.class );

    @Nullable
    default Calendar toCalendar( @Nullable MethodBuiltInInput in ) {
        return in == null ? null : new GregorianCalendar( 2020, Calendar.JANUARY, 15 );
    }

    @Mapping( target = "dateTime", source = "input" )
    MethodBuiltInTargetBean map( MethodBuiltInSourceBean source );
}
