/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.imports;

import org.mapstruct.Mapper;
import org.mapstruct.ap.test.imports.referenced.GenericMapper;
import org.mapstruct.ap.test.imports.referenced.Source;
import org.mapstruct.ap.test.imports.referenced.Target;
import org.mapstruct.ap.test.imports.to.FooWrapper;
import org.mapstruct.factory.Mappers;

/**
 * @author Andreas Gudian
 */
@Mapper(uses = GenericMapper.class)
public interface SecondSourceTargetMapper {

    SecondSourceTargetMapper INSTANCE = Mappers.getMapper( SecondSourceTargetMapper.class );

    FooWrapper fooWrapperToFooWrapper(org.mapstruct.ap.test.imports.from.FooWrapper foo);

    Target sourceToTarget(Source source);
}
