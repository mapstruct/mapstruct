/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.generics;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author Ben Zegveld
 */
@Mapper
public abstract class ErroneousGenericContainerMapperMismatch {

    public static final ErroneousGenericContainerMapperMismatch INSTANCE =
        Mappers.getMapper( ErroneousGenericContainerMapperMismatch.class );

    abstract <T, S> GenericTargetContainer<T> map(GenericSourceContainer<S> source);
}
