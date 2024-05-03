package org.mapstruct.ap.test.bugs._1830;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(
//    nullValueMapMappingStrategy = NullValueMappingStrategy.CLEAR, // TODO: IS this feature wanted?
//    nullValueIterableMappingStrategy = NullValueMappingStrategy.CLEAR // TODO: IS this feature wanted?
)
public interface BeanMapperWithValidConfig {

    BeanMapperWithValidConfig INSTANCE = Mappers.getMapper( BeanMapperWithValidConfig.class );

    BeanDTO map(Bean source, @MappingTarget BeanDTO target);

    BeanDTO map(NestedBean source, @MappingTarget BeanDTO target);

}
