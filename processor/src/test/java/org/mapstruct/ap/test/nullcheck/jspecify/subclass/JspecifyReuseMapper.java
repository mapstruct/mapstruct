/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nullcheck.jspecify.subclass;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.SubclassExhaustiveStrategy;
import org.mapstruct.SubclassMapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface JspecifyReuseMapper {

    JspecifyReuseMapper INSTANCE = Mappers.getMapper( JspecifyReuseMapper.class );

    @BeanMapping(subclassExhaustiveStrategy = SubclassExhaustiveStrategy.RUNTIME_EXCEPTION)
    @SubclassMapping(source = Source1.class, target = Target1.class)
    AbstractTarget map(AbstractSource s);

    TargetHolder map(SourceHolder h); // SourceHolder { Source1 child; } -> TargetHolder { Target1 child;
}
