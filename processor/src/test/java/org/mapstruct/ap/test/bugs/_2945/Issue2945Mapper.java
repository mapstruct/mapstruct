/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2945;

import org.mapstruct.Mapper;
import org.mapstruct.ap.test.bugs._2945._target.Target;
import org.mapstruct.ap.test.bugs._2945.source.Source;
import org.mapstruct.factory.Mappers;

/**
 * @author Filip Hrisafov
 */
@Mapper
public interface Issue2945Mapper {

    Issue2945Mapper INSTANCE = Mappers.getMapper( Issue2945Mapper.class );

    Target map(Source source);
}
