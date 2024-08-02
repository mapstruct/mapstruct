/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.generics;

import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * @author Ben Zegveld
 */
@Mapper
public abstract class GenericContainerMapper {

    public static final GenericContainerMapper INSTANCE = Mappers.getMapper( GenericContainerMapper.class );

    abstract <T> GenericTargetContainer<T> map(GenericSourceContainer<T> source);

    @Mapping( target = "contained", expression = "java(replacement)" )
    abstract <T, S> GenericTargetContainer<T> mapWithContext(GenericSourceContainer<S> source, @Context T replacement);

    @Mapping( target = "contained", source = "replacement" )
    @Mapping( target = "otherValue", source = "container.otherValue" )
    abstract <T, S> GenericTargetContainer<T> mapWithSecondParameter(GenericSourceContainer<S> container,
                                                                     T replacement);
}
