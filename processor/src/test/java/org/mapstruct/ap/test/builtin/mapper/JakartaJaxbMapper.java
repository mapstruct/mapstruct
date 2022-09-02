/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.builtin.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ap.test.builtin.bean.BigDecimalProperty;
import org.mapstruct.ap.test.builtin.bean.JakartaJaxbElementProperty;
import org.mapstruct.ap.test.builtin.bean.SomeType;
import org.mapstruct.ap.test.builtin.bean.SomeTypeProperty;
import org.mapstruct.ap.test.builtin.bean.StringProperty;
import org.mapstruct.factory.Mappers;

@Mapper
public interface JakartaJaxbMapper {

    JakartaJaxbMapper INSTANCE = Mappers.getMapper( JakartaJaxbMapper.class );

    StringProperty map(JakartaJaxbElementProperty source);

    BigDecimalProperty mapBD(JakartaJaxbElementProperty source);

    SomeTypeProperty mapSomeType(JakartaJaxbElementProperty source);

    @SuppressWarnings( "unused" )
    default SomeType map( String in ) {
        return new SomeType();
    }
}
