/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1648;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 * @author Filip Hrisafov
 */
@Mapper(unmappedSourcePolicy = ReportingPolicy.ERROR)
public interface Issue1648Mapper {

    Issue1648Mapper INSTANCE = Mappers.getMapper( Issue1648Mapper.class );

    @Mapping(target = "targetValue", source = "sourceValue")
    Target map(Source source);
}
