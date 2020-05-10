/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.conversion.optional;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(uses = OptionalMapper.class)
public interface SomeMapper {

    SomeMapper INSTANCE = Mappers.getMapper( SomeMapper.class );

    TargetWithPlainValues asPlain(SourceWithOptional source);

    void asPlain(SourceWithOptional source, @MappingTarget TargetWithPlainValues target);

    SourceWithOptional asOptional(TargetWithPlainValues target);

    void asOptional(TargetWithPlainValues target, @MappingTarget SourceWithOptional source);

}
