/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1650;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AMapper {

    AMapper INSTANCE = Mappers.getMapper( AMapper.class );

    @Mapping(source = "b.c", target = "CPrime")
    APrime toAPrime(A a, @MappingTarget APrime mappingTarget);

    CPrime toCPrime(C c, @MappingTarget CPrime mappingTarget);

    @Mapping(source = "b.c", target = "CPrime")
    APrime toAPrime(A a);

    CPrime toCPrime(C c);
}
