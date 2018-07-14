/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.builder.ignore;

import org.mapstruct.BeanMapping;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * @author Filip Hrisafov
 */
@Mapper(config = BuilderIgnoringMappingConfig.class)
public interface BuilderIgnoringMapper {

    BuilderIgnoringMapper INSTANCE = Mappers.getMapper( BuilderIgnoringMapper.class );

    @InheritConfiguration(name = "mapBase")
    Person mapWithIgnoringBase(PersonDto source);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "name", source = "name")
    Person mapOnlyWithExplicit(PersonDto source);

    Person mapAll(PersonDto source);
}
