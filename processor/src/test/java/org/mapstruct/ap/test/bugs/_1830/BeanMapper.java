package org.mapstruct.ap.test.bugs._1830;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper()
public interface BeanMapper {

    BeanMapper INSTANCE = Mappers.getMapper( BeanMapper.class );

    @Mapping(source = "list", target = "list", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.CLEAR)
    BeanDTO map(Bean source, @MappingTarget BeanDTO target);

    @Mapping(source = "bean.list", target = "list", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.CLEAR)
    BeanDTO map(NestedBean source, @MappingTarget BeanDTO target);

}
