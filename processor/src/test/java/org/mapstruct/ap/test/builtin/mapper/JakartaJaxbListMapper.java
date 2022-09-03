/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.builtin.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ap.test.builtin.bean.JakartaJaxbElementListProperty;
import org.mapstruct.ap.test.builtin.bean.StringListProperty;
import org.mapstruct.factory.Mappers;

@Mapper
public interface JakartaJaxbListMapper {

    JakartaJaxbListMapper INSTANCE = Mappers.getMapper( JakartaJaxbListMapper.class );

    StringListProperty map(JakartaJaxbElementListProperty source);
}
