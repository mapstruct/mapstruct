/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._880;

import org.mapstruct.Mapper;

/**
 * @author Andreas Gudian
 *
 */
@Mapper(componentModel = "default", config = Config.class)
public interface UsesConfigFromAnnotationMapper {
    Poodle metamorph(Object essence);
}
