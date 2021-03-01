/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nestedmethodcall;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * @author Sjaak Derksen
 */
@Mapper( uses = ObjectFactory.class )
public interface SourceTypeTargetDtoMapper {

    SourceTypeTargetDtoMapper INSTANCE = Mappers.getMapper( SourceTypeTargetDtoMapper.class );

    @Mapping(target = "date", source = "date", dateFormat = "dd.MM.yyyy")
    TargetDto sourceToTarget(SourceType source);

    SourceType targetToSource( TargetDto source );
}
