/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nullcheck.jspecify.builtin;

import org.mapstruct.Mapper;
import org.mapstruct.ap.test.nullcheck.jspecify.builtin.sources.JodaTimeBuildInSource;
import org.mapstruct.ap.test.nullcheck.jspecify.builtin.targets.JodaTimeBuildInTarget;
import org.mapstruct.factory.Mappers;

@Mapper
public interface JodaTimeBuildInMapper {

    JodaTimeBuildInMapper INSTANCE = Mappers.getMapper( JodaTimeBuildInMapper.class );

    JodaTimeBuildInTarget mapBuildIn(JodaTimeBuildInSource source);
}
