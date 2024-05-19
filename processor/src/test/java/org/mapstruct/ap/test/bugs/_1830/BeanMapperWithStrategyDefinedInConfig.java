package org.mapstruct.ap.test.bugs._1830;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.CLEAR)
public interface BeanMapperWithStrategyDefinedInConfig {

    @Mapping(source = "list", target = "list")
    BeanDTO map(Bean source, @MappingTarget BeanDTO target);

    @Mapping(source = "bean.list", target = "list")
    BeanDTO map(NestedBean source, @MappingTarget BeanDTO target);
}
