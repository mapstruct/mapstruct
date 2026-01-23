/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.conversion.nativetypes;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface OptionalNumberConversionMapper {

    OptionalNumberConversionMapper INSTANCE = Mappers.getMapper( OptionalNumberConversionMapper.class );

    ByteWrapperTarget sourceToTarget(ByteWrapperOptionalSource source);

    ByteWrapperOptionalSource targetToSource(ByteWrapperTarget target);

    ShortWrapperTarget sourceToTarget(ShortWrapperOptionalSource source);

    ShortWrapperOptionalSource targetToSource(ShortWrapperTarget target);

    IntTarget sourceToTarget(IntOptionalSource source);

    IntOptionalSource targetToSource(IntTarget target);

    IntWrapperTarget sourceToTarget(IntWrapperOptionalSource source);

    IntWrapperOptionalSource targetToSource(IntWrapperTarget target);

    LongTarget sourceToTarget(LongOptionalSource source);

    LongOptionalSource targetToSource(LongTarget target);

    LongWrapperTarget sourceToTarget(LongWrapperOptionalSource source);

    LongWrapperOptionalSource targetToSource(LongWrapperTarget target);

    FloatWrapperTarget sourceToTarget(FloatWrapperOptionalSource source);

    FloatWrapperOptionalSource targetToSource(FloatWrapperTarget target);

    DoubleTarget sourceToTarget(DoubleOptionalSource source);

    DoubleOptionalSource targetToSource(DoubleTarget target);

    DoubleWrapperTarget sourceToTarget(DoubleWrapperOptionalSource source);

    DoubleWrapperOptionalSource targetToSource(DoubleWrapperTarget target);

}
