/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.itest.modules.nullmarked;

import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.factory.Mappers;

/**
 * Mapper in a {@code @NullMarked} module with {@link NullValueCheckStrategy#ALWAYS}.
 * <p>
 * With the module (and package) in a {@code @NullMarked} scope, the source getter and target
 * setter parameter are both effectively {@code @NonNull}. The {@code ALWAYS} null-check must
 * therefore be suppressed: the target setter is called even when the source value is {@code null},
 * because the mapping is between two {@code @NonNull} types.
 */
@Mapper(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface NullMarkedMapper {

    NullMarkedMapper INSTANCE = Mappers.getMapper( NullMarkedMapper.class );

    NullMarkedTargetBean map(NullMarkedSourceBean source);
}
