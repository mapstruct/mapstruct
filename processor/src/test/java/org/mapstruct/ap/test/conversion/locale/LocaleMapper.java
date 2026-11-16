/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.conversion.locale;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface LocaleMapper {

    LocaleMapper INSTANCE = Mappers.getMapper( LocaleMapper.class );

    LocaleTarget sourceToTarget(LocaleSource source);

    LocaleSource targetToSource(LocaleTarget target);
}
