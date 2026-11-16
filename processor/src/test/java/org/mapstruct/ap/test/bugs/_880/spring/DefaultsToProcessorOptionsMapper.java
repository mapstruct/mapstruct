/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._880.spring;

import org.mapstruct.Mapper;

/**
 * @author Andreas Gudian
 *
 */
@Mapper
public interface DefaultsToProcessorOptionsMapper {
    Poodle metamorph(Object essence);
}
