/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.factories.targettype;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author Remo Meier
 */
@Mapper( uses = { Bar9Factory.class } )
public abstract class TargetTypeFactoryTestMapper {
    public static final TargetTypeFactoryTestMapper INSTANCE = Mappers.getMapper( TargetTypeFactoryTestMapper.class );

    public abstract Bar9Base foo9BaseToBar9Base(Foo9Base foo9);

    public abstract Bar9Child foo9ChildToBar9Child(Foo9Child foo9);
}
