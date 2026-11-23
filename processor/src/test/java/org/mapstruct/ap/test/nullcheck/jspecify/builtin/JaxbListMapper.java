/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nullcheck.jspecify.builtin;

import org.mapstruct.Mapper;
import org.mapstruct.ap.test.nullcheck.jspecify.builtin.sources.JaxbElementListProperty;
import org.mapstruct.ap.test.nullcheck.jspecify.builtin.targets.StringListProperty;
import org.mapstruct.factory.Mappers;

@Mapper
public interface JaxbListMapper {

    JaxbListMapper INSTANCE = Mappers.getMapper( JaxbListMapper.class );

    StringListProperty map(JaxbElementListProperty source);
}
