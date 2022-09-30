/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.injectionstrategy.spring.annotateWith;

import org.mapstruct.AnnotateWith;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

/**
 * @author Jose Carlos Campanero Ortiz
 */
@AnnotateWith(
    value = CustomStereotype.class,
    elements = @AnnotateWith.Element( strings = "AnnotateWithCustomStereotype" )
)
@Mapper( componentModel = MappingConstants.ComponentModel.SPRING )
public interface CustomerSpringCustomStereotypeQualifiedMapper {
}
