/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.conversion.bignumbers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BigIntegerOptionalMapper {

    BigIntegerOptionalMapper INSTANCE = Mappers.getMapper( BigIntegerOptionalMapper.class );

    BigIntegerTarget sourceToTarget(BigIntegerOptionalSource source);

    BigIntegerOptionalSource targetToSource(BigIntegerTarget target);
}
