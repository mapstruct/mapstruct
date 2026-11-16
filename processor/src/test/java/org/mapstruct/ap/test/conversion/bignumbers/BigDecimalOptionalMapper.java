/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.conversion.bignumbers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BigDecimalOptionalMapper {

    BigDecimalOptionalMapper INSTANCE = Mappers.getMapper( BigDecimalOptionalMapper.class );

    BigDecimalTarget sourceToTarget(BigDecimalOptionalSource source);

    BigDecimalOptionalSource targetToSource(BigDecimalTarget target);
}
