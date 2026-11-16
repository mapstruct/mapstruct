/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._3668;

import org.mapstruct.Mapper;
import org.mapstruct.SubclassExhaustiveStrategy;
import org.mapstruct.SubclassMapping;

@Mapper(uses = { ChildMapper.class }, subclassExhaustiveStrategy = SubclassExhaustiveStrategy.RUNTIME_EXCEPTION)
public interface ParentMapper {

    @SubclassMapping(target = Parent.ParentA.class, source = ParentDto.ParentDtoA.class)
    @SubclassMapping(target = Parent.ParentB.class, source = ParentDto.ParentDtoB.class)
    Parent<?> toEntity(ParentDto<?> parentDto);

    @SubclassMapping(target = ParentDto.ParentDtoA.class, source = Parent.ParentA.class)
    @SubclassMapping(target = ParentDto.ParentDtoB.class, source = Parent.ParentB.class)
    ParentDto<?> toDto(Parent<?> parent);

    Parent.ParentA toEntity(ParentDto.ParentDtoA parentDto);

    ParentDto.ParentDtoA toDto(Parent.ParentA parent);

    Parent.ParentB toEntity(ParentDto.ParentDtoB parentDto);

    ParentDto.ParentDtoB toDto(Parent.ParentB parent);

}
