/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.builder.multiple;

import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.ap.test.builder.multiple.build.Process;

/**
 * @author Filip Hrisafov
 */
@Mapper(builder = @Builder(buildMethod = "mapperBuild"))
public interface ErroneousMoreThanOneBuildMethodWithMapperDefinedMappingMapper {

    Process map(Source source);
}
