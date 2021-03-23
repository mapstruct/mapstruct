/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.conversion.uuid;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper()
public interface UUIDMapper {

    UUIDMapper INSTANCE = Mappers.getMapper( UUIDMapper.class );

    UUIDTarget sourceToTarget(UUIDSource source);

    UUIDSource targetToSource(UUIDTarget target);
}
