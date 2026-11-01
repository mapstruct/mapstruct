/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nullcheck.jspecify.builtin;

import org.mapstruct.Mapper;
import org.mapstruct.ap.test.nullcheck.jspecify.builtin.sources.JaxbElementProperty;
import org.mapstruct.ap.test.nullcheck.jspecify.builtin.targets.BigDecimalProperty;
import org.mapstruct.ap.test.nullcheck.jspecify.builtin.targets.SomeType;
import org.mapstruct.ap.test.nullcheck.jspecify.builtin.targets.SomeTypeProperty;
import org.mapstruct.ap.test.nullcheck.jspecify.builtin.targets.StringProperty;
import org.mapstruct.factory.Mappers;

@Mapper
public interface JaxbMapper {

    JaxbMapper INSTANCE = Mappers.getMapper( JaxbMapper.class );

    StringProperty map(JaxbElementProperty source);

    BigDecimalProperty mapBD(JaxbElementProperty source);

    SomeTypeProperty mapSomeType(JaxbElementProperty source);

    default SomeType map(String in ) {
        return new SomeType();
    }
}
