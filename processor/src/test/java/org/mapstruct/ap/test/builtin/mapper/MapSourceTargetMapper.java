/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.builtin.mapper;

import java.util.Map;

import javax.xml.bind.JAXBElement;
import javax.xml.datatype.XMLGregorianCalendar;

import org.mapstruct.Mapper;
import org.mapstruct.ap.test.builtin._target.MapTarget;
import org.mapstruct.ap.test.builtin.source.MapSource;
import org.mapstruct.factory.Mappers;

@Mapper
public interface MapSourceTargetMapper {

    MapSourceTargetMapper INSTANCE = Mappers.getMapper( MapSourceTargetMapper.class );

    MapTarget sourceToTarget(MapSource source);

    Map<String, String> longDateMapToStringStringMap(Map<JAXBElement<String>, XMLGregorianCalendar> source);
}
