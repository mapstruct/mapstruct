/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.ignored;

import org.mapstruct.Ignored;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ZooMapper {

    ZooMapper INSTANCE = Mappers.getMapper( ZooMapper.class );

    @Ignored( prefix = "animal", targets = { "publicAge", "size", "publicColor", "age", "color" } )
    ZooDto zooToDto( Zoo zoo );

    @Ignored( targets = { "address" } )
    @Ignored( prefix = "animal", targets = { "publicAge", "size", "publicColor", "age", "color" } )
    ZooDto zooToDto2( Zoo zoo );
}
