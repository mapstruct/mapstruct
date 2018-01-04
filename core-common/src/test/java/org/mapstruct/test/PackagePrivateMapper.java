package org.mapstruct.test;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
interface PackagePrivateMapper {
    PackagePrivateMapper INSTANCE = Mappers.getMapper(PackagePrivateMapper.class);
}
