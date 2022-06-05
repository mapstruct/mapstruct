/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.injectionstrategy.spring.annotateWith;

import org.springframework.stereotype.Component;

import org.mapstruct.AnnotateWith;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

/**
 * @author Ben Zegveld
 */
@AnnotateWith( value = Component.class, elements = @AnnotateWith.Element( strings = "AnnotateWith" ) )
@Mapper( componentModel = MappingConstants.ComponentModel.SPRING, uses = GenderSpringDefaultMapper.class )
public interface CustomerSpringComponentQualifiedMapper extends CustomerSpringDefaultMapper {
}
