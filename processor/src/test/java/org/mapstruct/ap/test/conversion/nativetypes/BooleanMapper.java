/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.conversion.nativetypes;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BooleanMapper {

    BooleanMapper INSTANCE = Mappers.getMapper( BooleanMapper.class );

    BooleanTarget sourceToTarget(BooleanSource source);

    BooleanSource targetToSource(BooleanTarget target);
}
