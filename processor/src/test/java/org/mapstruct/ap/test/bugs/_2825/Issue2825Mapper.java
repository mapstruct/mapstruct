/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2825;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.SubclassMapping;
import org.mapstruct.factory.Mappers;

/**
 * @author orange add
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface Issue2825Mapper {

    Issue2825Mapper INSTANCE = Mappers.getMapper( Issue2825Mapper.class );

    @SubclassMapping(target = TargetAnimal.class, source = Dog.class)
    @SubclassMapping(target = TargetAnimal.class, source = Cat.class)
    TargetAnimal map(Animal source);
}
