/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.source.ignore;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * @author Filip Hrisafov
 */
@Mapper(
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    unmappedSourcePolicy = ReportingPolicy.ERROR
)
public interface IgnoreUnmappedSourceMapper {

    @BeanMapping(
        ignoreUnmappedSourceProperties = {
            "name",
            "surname"
        }
    )
    PersonDto map(Person person);
}
