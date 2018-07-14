/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.builder.multiple;

import org.mapstruct.BeanMapping;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.ap.test.builder.multiple.build.Process;

/**
 * @author Filip Hrisafov
 */
@Mapper
public interface ErroneousMoreThanOneBuildMethodMapper {

    Process map(Source source);

    @BeanMapping(builder = @Builder(buildMethod = "missingBuild"))
    Process map2(Source source);
}
