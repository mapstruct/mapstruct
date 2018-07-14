/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.callbacks.returning;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author Pascal Grün
 */
@Mapper
public abstract class NumberMapperDefault {
    public static final NumberMapperDefault INSTANCE = Mappers.getMapper( NumberMapperDefault.class );

    public abstract Number integerToNumber(Integer number);
}
