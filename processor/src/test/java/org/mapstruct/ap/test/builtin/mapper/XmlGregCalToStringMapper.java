/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.builtin.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ap.test.builtin.bean.StringProperty;
import org.mapstruct.ap.test.builtin.bean.XmlGregorianCalendarProperty;
import org.mapstruct.factory.Mappers;

@Mapper
public interface XmlGregCalToStringMapper {

    XmlGregCalToStringMapper INSTANCE = Mappers.getMapper( XmlGregCalToStringMapper.class );

    StringProperty map(XmlGregorianCalendarProperty source);

    @Mappings( {
        @Mapping(target = "prop", dateFormat = "dd.MM.yyyy"),
        @Mapping(target = "publicProp", dateFormat = "dd.MM.yyyy")
    } )
    StringProperty mapAndFormat(XmlGregorianCalendarProperty source);

}
