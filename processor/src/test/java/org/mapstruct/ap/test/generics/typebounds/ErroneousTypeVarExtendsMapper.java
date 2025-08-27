/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.generics.typebounds;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author Ben Zegveld
 */
@Mapper
public abstract class ErroneousTypeVarExtendsMapper {
    public static final ErroneousTypeVarExtendsMapper INSTANCE =
        Mappers.getMapper( ErroneousTypeVarExtendsMapper.class );

    // not supported yet, use `? extends WildcardImpl` in the map method instead.
    public abstract <L extends WildcardImpl> Target map(Source<L> action);

    String mapToString(WildcardImpl rawData) {
        return rawData.getContents();
    }
}
