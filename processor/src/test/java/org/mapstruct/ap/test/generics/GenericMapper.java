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
public abstract class GenericMapper {

    public static final GenericMapper INSTANCE = Mappers.getMapper( GenericMapper.class );

    abstract <T> GenericTargetContainer<T> map(GenericSourceContainer<T> source);
}
