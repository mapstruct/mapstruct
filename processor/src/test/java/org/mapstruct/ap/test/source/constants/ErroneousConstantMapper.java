/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.source.constants;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
 *
 * @author Sjaak Derksen
 */
@Mapper
public interface ErroneousConstantMapper {

    ErroneousConstantMapper INSTANCE = Mappers.getMapper( ErroneousConstantMapper.class );

    @BeanMapping( ignoreByDefault = true )
    @Mapping(target = "booleanValue", constant = "zz")
    @Mapping(target = "charValue", constant = "'ba'")
    @Mapping(target = "byteValue", constant = "200")
    @Mapping(target = "intValue", constant = "0xFFFF_FFFF_FFFF")
    @Mapping(target = "longValue", constant = "1")
    @Mapping(target = "floatValue", constant = "1.40e-_45f")
    @Mapping(target = "doubleValue", constant = "1e-137000")
    ConstantsTarget mapFromConstants( String dummy );
}
