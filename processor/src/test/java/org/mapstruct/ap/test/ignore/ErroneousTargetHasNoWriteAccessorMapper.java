/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.ignore;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 *
 * @author Sjaak Derksen
 */
@Mapper
public interface ErroneousTargetHasNoWriteAccessorMapper {

    ErroneousTargetHasNoWriteAccessorMapper INSTANCE =
        Mappers.getMapper( ErroneousTargetHasNoWriteAccessorMapper.class );

    @Mapping(target = "hasClaws", constant = "true")
    PreditorDto preditorToDto( Preditor preditor );

}
