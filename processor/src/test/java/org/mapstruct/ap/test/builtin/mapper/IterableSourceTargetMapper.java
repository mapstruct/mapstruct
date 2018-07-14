/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.builtin.mapper;

import java.util.List;

import javax.xml.datatype.XMLGregorianCalendar;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.ap.test.builtin._target.IterableTarget;
import org.mapstruct.ap.test.builtin.source.IterableSource;
import org.mapstruct.factory.Mappers;

@Mapper
public interface IterableSourceTargetMapper {

    IterableSourceTargetMapper INSTANCE = Mappers.getMapper( IterableSourceTargetMapper.class );

    IterableTarget sourceToTarget(IterableSource source);

    @IterableMapping(dateFormat = "dd.MM.yyyy")
    List<String> stringListToDateList(List<XMLGregorianCalendar> dates);
}
