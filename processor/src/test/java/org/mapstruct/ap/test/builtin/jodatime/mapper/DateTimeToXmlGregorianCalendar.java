/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.builtin.jodatime.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ap.test.builtin.jodatime.bean.DateTimeBean;
import org.mapstruct.ap.test.builtin.jodatime.bean.XmlGregorianCalendarBean;
import org.mapstruct.factory.Mappers;

/**
 *
 * @author Sjaak Derksen
 */
@Mapper
public interface DateTimeToXmlGregorianCalendar {

    DateTimeToXmlGregorianCalendar INSTANCE = Mappers.getMapper( DateTimeToXmlGregorianCalendar.class );

    @Mapping( target = "XMLGregorianCalendar", source = "dateTime")
    XmlGregorianCalendarBean toXmlGregorianCalendarBean( DateTimeBean in );
}
