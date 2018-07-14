/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.conversion.bignumbers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BigIntegerMapper {

    BigIntegerMapper INSTANCE = Mappers.getMapper( BigIntegerMapper.class );

    BigIntegerTarget sourceToTarget(BigIntegerSource source);

    BigIntegerSource targetToSource(BigIntegerTarget target);
}
