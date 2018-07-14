/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.java8stream.forged;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ErroneousStreamNonMappableStreamMapper {

    ErroneousStreamNonMappableStreamMapper INSTANCE =
        Mappers.getMapper( ErroneousStreamNonMappableStreamMapper.class );

    ErroneousNonMappableStreamTarget sourceToTarget(ErroneousNonMappableStreamSource source);
}
