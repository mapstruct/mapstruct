/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nullcheck.jspecify.builtin;

import org.jspecify.annotations.NullMarked;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ap.test.nullcheck.jspecify.builtin.sources.XMLGregorianCalendarBuiltinSource;
import org.mapstruct.ap.test.nullcheck.jspecify.builtin.targets.XMLGregorianCalendarBuiltinTarget;
import org.mapstruct.factory.Mappers;

@Mapper
@NullMarked
public interface XMLGregorianCalendarBuiltinMapper {
    XMLGregorianCalendarBuiltinMapper INSTANCE = Mappers.getMapper( XMLGregorianCalendarBuiltinMapper.class );

    @Mapping(target = "publicStr", source = "publicXml", dateFormat = "dd.MM.yyyy" )
    @Mapping(target = "publicXml", source = "publicStr", dateFormat = "dd.MM.yyyy" )
    @Mapping(target = "str", source = "xml", dateFormat = "dd.MM.yyyy" )
    @Mapping(target = "xml", source = "str", dateFormat = "dd.MM.yyyy" )
    XMLGregorianCalendarBuiltinTarget map(XMLGregorianCalendarBuiltinSource source);
}
