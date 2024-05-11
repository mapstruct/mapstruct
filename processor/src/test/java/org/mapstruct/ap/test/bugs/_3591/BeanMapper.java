package org.mapstruct.ap.test.bugs._3591;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BeanMapper {

    BeanMapper INSTANCE = Mappers.getMapper( BeanMapper.class );

    @Mapping(source = "beans", target = "beans")
    BeanDto map(Bean bean, @MappingTarget BeanDto beanDto);

}
