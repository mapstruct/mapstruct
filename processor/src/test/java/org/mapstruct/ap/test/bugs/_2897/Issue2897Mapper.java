/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2897;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ap.test.bugs._2897.util.Util;
import org.mapstruct.factory.Mappers;

/**
 * @author Filip Hrisafov
 */
@Mapper(imports = Util.Factory.class)
public interface Issue2897Mapper {

    Issue2897Mapper INSTANCE = Mappers.getMapper( Issue2897Mapper.class );

    @Mapping( target = "value", expression = "java(Factory.parse( source ))")
    Target map(Source source);
}
