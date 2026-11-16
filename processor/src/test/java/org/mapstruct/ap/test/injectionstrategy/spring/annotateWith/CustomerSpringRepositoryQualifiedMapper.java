/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.injectionstrategy.spring.annotateWith;

import org.mapstruct.AnnotateWith;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.springframework.stereotype.Repository;

/**
 * @author Jose Carlos Campanero Ortiz
 */
@AnnotateWith( value = Repository.class, elements = @AnnotateWith.Element( strings = "AnnotateWithRepository" ) )
@Mapper( componentModel = MappingConstants.ComponentModel.SPRING )
public interface CustomerSpringRepositoryQualifiedMapper {
}
