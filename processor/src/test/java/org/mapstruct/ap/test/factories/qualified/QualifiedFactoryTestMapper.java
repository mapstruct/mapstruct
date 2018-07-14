/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.factories.qualified;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author Remo Meier
 */
@Mapper( uses = { Bar10Factory.class } )
public abstract class QualifiedFactoryTestMapper {
    public static final QualifiedFactoryTestMapper INSTANCE = Mappers.getMapper( QualifiedFactoryTestMapper.class );

    public abstract Bar10 foo10ToBar10Lower(Foo10 foo10);

    @BeanMapping( qualifiedBy = TestQualifier.class )
    public abstract Bar10 foo10ToBar10Upper(Foo10 foo10);

    @BeanMapping( qualifiedByName = "Bar10NamedQualifier" )
    public abstract Bar10 foo10ToBar10Camel(Foo10 foo10);

}
