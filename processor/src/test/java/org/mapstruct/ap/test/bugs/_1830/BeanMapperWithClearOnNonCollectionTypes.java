package org.mapstruct.ap.test.bugs._1830;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper()
public interface BeanMapperWithClearOnNonCollectionTypes {

    BeanMapperWithClearOnNonCollectionTypes INSTANCE = Mappers.getMapper( BeanMapperWithClearOnNonCollectionTypes.class );

    @Mapping(target = "id", source = "id", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.CLEAR)
    BeanDTOWithId map(BeanWithId source, @MappingTarget BeanDTOWithId target);
}
