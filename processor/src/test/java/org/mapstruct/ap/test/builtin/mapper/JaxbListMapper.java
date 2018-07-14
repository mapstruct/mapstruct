/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.builtin.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ap.test.builtin.bean.JaxbElementListProperty;
import org.mapstruct.ap.test.builtin.bean.StringListProperty;
import org.mapstruct.factory.Mappers;

@Mapper
public interface JaxbListMapper {

    JaxbListMapper INSTANCE = Mappers.getMapper( JaxbListMapper.class );

    StringListProperty map(JaxbElementListProperty source);
}
