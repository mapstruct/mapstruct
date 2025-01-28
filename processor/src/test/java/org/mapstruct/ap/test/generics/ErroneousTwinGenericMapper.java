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
public interface ErroneousTwinGenericMapper {
    ErroneousTwinGenericMapper INSTANCE = Mappers.getMapper( ErroneousTwinGenericMapper.class );

    <A, B> TwinGenericTarget<A, B> map(TwinGenericSource<B, A> source);
}
