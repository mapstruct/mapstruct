/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.injectionstrategy.spring.annotateWith.decorator;

import org.mapstruct.AnnotateWith;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.springframework.stereotype.Component;

/**
 * @author Ben Zegveld
 * @author Jose Carlos Campanero Ortiz
 */
@AnnotateWith( value = Component.class, elements = @AnnotateWith.Element( strings = "AnnotateWithComponent" ) )
@Mapper( componentModel = MappingConstants.ComponentModel.SPRING, uses = GenderSpringDefaultMapper.class )
@DecoratedWith(UpperCaseCustomerNameSpringDefaultMapperDecorator.class)
public interface CustomerSpringComponentQualifiedMapper extends CustomerSpringDefaultMapper {
}
