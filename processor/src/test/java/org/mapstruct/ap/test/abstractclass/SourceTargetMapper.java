/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.abstractclass;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(uses = ReferencedMapper.class)
public abstract class SourceTargetMapper extends AbstractBaseMapper {

    public static final SourceTargetMapper INSTANCE = Mappers.getMapper( SourceTargetMapper.class );

    public abstract Target sourceToTarget(Source source);

    protected String calendarToString(Calendar calendar) {
        DateFormat format = new SimpleDateFormat( "dd.MM.yyyy" );
        return "Birthday: " + format.format( calendar.getTime() );
    }
}
