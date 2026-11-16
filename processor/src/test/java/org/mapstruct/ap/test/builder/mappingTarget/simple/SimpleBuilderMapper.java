/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.builder.mappingTarget.simple;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper
public interface SimpleBuilderMapper {

    SimpleBuilderMapper INSTANCE = Mappers.getMapper( SimpleBuilderMapper.class );

    @Mapping(target = "builder.name", source = "source.fullName")
    SimpleImmutableTarget toImmutable(SimpleMutableSource source, @MappingTarget SimpleImmutableTarget.Builder builder);

    @Mapping(target = "builder.name", source = "source.fullName")
    void updateImmutable(SimpleMutableSource source, @MappingTarget SimpleImmutableTarget.Builder builder);

    @Mapping(target = "name", source = "fullName")
    SimpleImmutableTarget toImmutable(SimpleMutableSource source);

    @Mapping(target = "name", source = "fullName")
    MutableTarget toMutableTarget(SimpleMutableSource simpleMutableSource);

    @Mapping(target = "name", source = "fullName")
    void updateMutableTarget(SimpleMutableSource simpleMutableSource, @MappingTarget MutableTarget target);
}
