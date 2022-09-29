/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.injectionstrategy.spring.annotateWith.qualified;

import org.mapstruct.AnnotateWith;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.springframework.stereotype.Controller;

/**
 * @author Jose Carlos Campanero Ortiz
 */
@AnnotateWith( value = Controller.class, elements = @AnnotateWith.Element( strings = "AnnotateWithController" ) )
@Mapper( componentModel = MappingConstants.ComponentModel.SPRING )
public interface CustomerSpringControllerQualifiedMapper {
}
