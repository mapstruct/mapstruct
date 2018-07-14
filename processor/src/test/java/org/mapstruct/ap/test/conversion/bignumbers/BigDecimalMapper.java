/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.conversion.bignumbers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BigDecimalMapper {

    BigDecimalMapper INSTANCE = Mappers.getMapper( BigDecimalMapper.class );

    BigDecimalTarget sourceToTarget(BigDecimalSource source);

    BigDecimalSource targetToSource(BigDecimalTarget target);
}
