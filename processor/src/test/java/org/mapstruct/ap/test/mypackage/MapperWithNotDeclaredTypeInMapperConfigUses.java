/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */

package org.mapstruct.ap.test.mypackage;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author Andrei Arlou
 */
@Mapper(config = MapperConfigWithNotDecladerTypeInUses.class)
public interface MapperWithNotDeclaredTypeInMapperConfigUses {

    MapperWithNotDeclaredTypeInMapperConfigUses INSTANCE =
                        Mappers.getMapper( MapperWithNotDeclaredTypeInMapperConfigUses.class );

    Target map(Source source);
}