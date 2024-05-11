/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.generics;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * @author Ben Zegveld
 */
@Mapper
public abstract class ErroneousGenericContainerMapperParameterMismatch {

    public static final ErroneousGenericContainerMapperParameterMismatch INSTANCE =
        Mappers.getMapper( ErroneousGenericContainerMapperParameterMismatch.class );

    @Mapping( target = "contained", source = "replacement")
    abstract <T, S> GenericTargetContainer<S> map(GenericSourceContainer<T> source, T replacement);
}
