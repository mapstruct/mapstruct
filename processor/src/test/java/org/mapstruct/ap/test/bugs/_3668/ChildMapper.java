/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._3668;

import org.mapstruct.Mapper;
import org.mapstruct.SubclassExhaustiveStrategy;
import org.mapstruct.SubclassMapping;

@Mapper(subclassExhaustiveStrategy = SubclassExhaustiveStrategy.RUNTIME_EXCEPTION)
public interface ChildMapper {

    @SubclassMapping(target = Child.ChildA.class, source = ChildDto.ChildDtoA.class)
    @SubclassMapping(target = Child.ChildB.class, source = ChildDto.ChildDtoB.class)
    Child toEntity(ChildDto childDto);

    @SubclassMapping(target = ChildDto.ChildDtoA.class, source = Child.ChildA.class)
    @SubclassMapping(target = ChildDto.ChildDtoB.class, source = Child.ChildB.class)
    ChildDto toDto(Child child);

    Child.ChildA toEntity(ChildDto.ChildDtoA childDto);

    ChildDto.ChildDtoA toDto(Child.ChildA child);

    Child.ChildB toEntity(ChildDto.ChildDtoB childDto);

    ChildDto.ChildDtoB toDto(Child.ChildB child);

}
