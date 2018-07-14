/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.itest.naming;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface GolfPlayerMapper {

    GolfPlayerMapper INSTANCE = Mappers.getMapper( GolfPlayerMapper.class );

    GolfPlayerDto golfPlayerToDto(GolfPlayer player);
}
