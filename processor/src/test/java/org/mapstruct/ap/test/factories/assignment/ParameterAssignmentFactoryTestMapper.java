/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.factories.assignment;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author Remo Meier
 */
@Mapper( uses = { Bar5Factory.class, Bar6Factory.class, Bar7Factory.class } )
public abstract class ParameterAssignmentFactoryTestMapper {
    public static final ParameterAssignmentFactoryTestMapper INSTANCE =
        Mappers.getMapper( ParameterAssignmentFactoryTestMapper.class );

    public abstract Bar5 foos5ToBar5(Foo5A foo5A, Foo5B foo5B);

    public abstract Bar6 foos6ToBar6(Foo6A foo6A, Foo6B foo6B);

    public abstract Bar7 foos7ToBar7(Foo7A foo7A, Foo7B foo7B);
}
