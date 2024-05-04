package org.mapstruct.ap.test.bugs._1830;

import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.CLEAR)
// TODO: This should lead to an compile error
public interface MapperWithErrorInConfig {

    BeanWithId map(BeanDTOWithId beanDTOWithId);
}
