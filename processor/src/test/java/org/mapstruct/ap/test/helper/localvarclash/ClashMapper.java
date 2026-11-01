/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.helper.localvarclash;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.jspecify.annotations.Nullable;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * Same shape as {@code MethodBuiltInMapper}, but the source method's parameter is named
 * {@code clashInput}, which is exactly {@code decapitalize("ClashInput")} — the name the
 * {@link org.mapstruct.ap.internal.model.NullSafe2StepMappingMethod} helper gives to its local
 * variable for the intermediate result. In the generated helper this produces
 * {@code Calendar clashInput = toCalendar( clashInput );} inside a method whose parameter is also
 * named {@code clashInput}, i.e. a duplicate variable that does not compile.
 */
@Mapper
public interface ClashMapper {

    ClashMapper INSTANCE = Mappers.getMapper( ClashMapper.class );

    @Nullable
    default Calendar toCalendar( @Nullable ClashInput clashInput ) {
        return clashInput == null ? null : new GregorianCalendar( 2020, Calendar.JANUARY, 15 );
    }

    @Mapping( target = "dateTime", source = "clashInput" )
    ClashTargetBean map( ClashSourceBean source );
}
