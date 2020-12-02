/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.annotations;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author Raimund Klein
 */
@Mapper(implementationAnnotations = MyGeneratedAnnotation.class)
public interface AnnotatedMapper {
    AnnotatedMapper INSTANCE = Mappers.getMapper(AnnotatedMapper.class);

    Target map(Source source);
}
