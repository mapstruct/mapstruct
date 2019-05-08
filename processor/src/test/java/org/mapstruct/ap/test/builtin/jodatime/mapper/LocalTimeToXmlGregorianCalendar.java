/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.builtin.jodatime.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ap.test.builtin.jodatime.bean.LocalTimeBean;
import org.mapstruct.ap.test.builtin.jodatime.bean.XmlGregorianCalendarBean;
import org.mapstruct.factory.Mappers;

/**
 *
 * @author Sjaak Derksen
 */
@Mapper
public interface LocalTimeToXmlGregorianCalendar {

    LocalTimeToXmlGregorianCalendar INSTANCE = Mappers.getMapper( LocalTimeToXmlGregorianCalendar.class );

    @Mapping( target = "XMLGregorianCalendar", source = "localTime")
    XmlGregorianCalendarBean toXmlGregorianCalendarBean( LocalTimeBean in );

}
