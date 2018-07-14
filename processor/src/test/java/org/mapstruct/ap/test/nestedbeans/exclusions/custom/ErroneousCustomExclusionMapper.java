/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nestedbeans.exclusions.custom;

import org.mapstruct.Mapper;

/**
 * @author Filip Hrisafov
 */
// tag::documentation[]
@Mapper
public interface ErroneousCustomExclusionMapper {

    Target map(Source source);
}
// end::documentation[]
