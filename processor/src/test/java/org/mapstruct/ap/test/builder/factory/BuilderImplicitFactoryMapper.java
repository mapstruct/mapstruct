/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.builder.factory;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author Filip Hrisafov
 */
@Mapper
public abstract class BuilderImplicitFactoryMapper {

    public static final BuilderImplicitFactoryMapper INSTANCE = Mappers.getMapper( BuilderImplicitFactoryMapper.class );

    public abstract Person map(PersonDto source);

    public Person.PersonBuilder personBuilder() {
        return new Person.PersonBuilder( "Implicit Factory" );
    }

}
