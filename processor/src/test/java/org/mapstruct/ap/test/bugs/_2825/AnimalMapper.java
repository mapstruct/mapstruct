/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2825;

import org.mapstruct.Mapper;
import org.mapstruct.SubclassMapping;
import org.mapstruct.factory.Mappers;

/**
 * @author orange add
 */
@Mapper
public interface AnimalMapper {

    AnimalMapper INSTANCE = Mappers.getMapper( AnimalMapper.class );

    @SubclassMapping(target = TargetAnimal.class, source = Dog.class)
    @SubclassMapping(target = TargetAnimal.class, source = Cat.class)
    TargetAnimal map(Animal source);
}
