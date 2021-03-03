/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._891;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 *
 * @author Sjaak Derksen
 */
@Mapper
public interface BuggyMapper {

    BuggyMapper INSTANCE = Mappers.getMapper( BuggyMapper.class );

    @Mapping(target = "propLong", source = "nested.propInt")
    Dest convert(Src src);
}
