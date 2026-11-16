/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1395.spring;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

/**
 * @author Filip Hrisafov
 */
@Mapper(injectionStrategy = InjectionStrategy.CONSTRUCTOR, componentModel = MappingConstants.ComponentModel.SPRING,
    uses = NotUsedService.class)
public interface Issue1395Mapper {

    Target map(Source source);
}
