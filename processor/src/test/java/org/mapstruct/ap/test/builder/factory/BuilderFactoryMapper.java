/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.builder.factory;

import org.mapstruct.Mapper;
import org.mapstruct.ObjectFactory;
import org.mapstruct.factory.Mappers;

/**
 * @author Filip Hrisafov
 */
@Mapper
public abstract class BuilderFactoryMapper {

    public static final BuilderFactoryMapper INSTANCE = Mappers.getMapper( BuilderFactoryMapper.class );

    public abstract Person map(PersonDto source);

    @ObjectFactory
    public Person.PersonBuilder personBuilder() {
        return new Person.PersonBuilder( "Factory with @ObjectFactory" );
    }

}
