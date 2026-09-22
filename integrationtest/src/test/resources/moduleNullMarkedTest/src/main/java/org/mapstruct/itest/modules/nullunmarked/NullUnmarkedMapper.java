/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.itest.modules.nullunmarked;

import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.factory.Mappers;

/**
 * Mapper in a {@code @NullUnmarked} package (inside a {@code @NullMarked} module) with
 * {@link NullValueCheckStrategy#ALWAYS}.
 * <p>
 * The package-level {@code @NullUnmarked} reverses the module-level {@code @NullMarked} scope.
 * The source getter and target setter parameter therefore have UNKNOWN nullability and the
 * {@code ALWAYS} null-check strategy applies: the target setter is NOT called when the source
 * value is {@code null}.
 */
@Mapper(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface NullUnmarkedMapper {

    NullUnmarkedMapper INSTANCE = Mappers.getMapper( NullUnmarkedMapper.class );

    NullUnmarkedTargetBean map(NullUnmarkedSourceBean source);
}
