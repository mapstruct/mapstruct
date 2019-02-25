/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.builtin.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ap.test.builtin.bean.BigDecimalProperty;
import org.mapstruct.ap.test.builtin.bean.SomeType;
import org.mapstruct.ap.test.builtin.bean.SomeTypeProperty;
import org.mapstruct.ap.test.builtin.bean.JaxbElementProperty;
import org.mapstruct.ap.test.builtin.bean.StringProperty;
import org.mapstruct.factory.Mappers;

@Mapper
public interface JaxbMapper {

    JaxbMapper INSTANCE = Mappers.getMapper( JaxbMapper.class );

    StringProperty map(JaxbElementProperty source);

    BigDecimalProperty mapBD(JaxbElementProperty source);

    SomeTypeProperty mapSomeType(JaxbElementProperty source);

    default SomeType map( String in ) {
        return new SomeType();
    }
}
