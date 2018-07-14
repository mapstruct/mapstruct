/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.conversion.nativetypes;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface SourceTargetMapper {

    SourceTargetMapper INSTANCE = Mappers.getMapper( SourceTargetMapper.class );

    ByteTarget sourceToTarget(ByteSource source);

    ByteSource targetToSource(ByteTarget target);

    ByteWrapperTarget sourceToTarget(ByteWrapperSource source);

    ByteWrapperSource targetToSource(ByteWrapperTarget target);

    ShortTarget sourceToTarget(ShortSource source);

    ShortSource targetToSource(ShortTarget target);

    ShortWrapperTarget sourceToTarget(ShortWrapperSource source);

    ShortWrapperSource targetToSource(ShortWrapperTarget target);

    IntTarget sourceToTarget(IntSource source);

    IntSource targetToSource(IntTarget target);

    IntWrapperTarget sourceToTarget(IntWrapperSource source);

    IntWrapperSource targetToSource(IntWrapperTarget target);

    LongTarget sourceToTarget(LongSource source);

    LongSource targetToSource(LongTarget target);

    LongWrapperTarget sourceToTarget(LongWrapperSource source);

    LongWrapperSource targetToSource(LongWrapperTarget target);

    FloatTarget sourceToTarget(FloatSource source);

    FloatSource targetToSource(FloatTarget target);

    FloatWrapperTarget sourceToTarget(FloatWrapperSource source);

    FloatWrapperSource targetToSource(FloatWrapperTarget target);

    DoubleTarget sourceToTarget(DoubleSource source);

    DoubleSource targetToSource(DoubleTarget target);

    DoubleWrapperTarget sourceToTarget(DoubleWrapperSource source);

    DoubleWrapperSource targetToSource(DoubleWrapperTarget target);

}
