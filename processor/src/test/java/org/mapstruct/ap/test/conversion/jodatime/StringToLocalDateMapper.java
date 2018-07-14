/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.conversion.jodatime;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface StringToLocalDateMapper {

    StringToLocalDateMapper INSTANCE = Mappers.getMapper( StringToLocalDateMapper.class );

    TargetWithLocalDate sourceToTarget(SourceWithStringDate source);
}
