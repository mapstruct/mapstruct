package org.mapstruct.ap.test.unmappedtarget.beanmapping;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BeanMappingIgnoreTargetMapper {

    BeanMappingIgnoreTargetMapper INSTANCE =
            Mappers.getMapper( BeanMappingIgnoreTargetMapper.class );

    @BeanMapping( ignoreTargets = {"nested.flag", "address"} )
    Target convert( Source source );
}
