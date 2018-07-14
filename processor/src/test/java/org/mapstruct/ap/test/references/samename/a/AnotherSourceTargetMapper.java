/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.references.samename.a;

import org.mapstruct.Mapper;
import org.mapstruct.ap.test.references.samename.model.Source;
import org.mapstruct.ap.test.references.samename.model.Target;
import org.mapstruct.factory.Mappers;

/**
 * @author Gunnar Morling
 */
@Mapper(uses = {
    CustomMapper.class,
    org.mapstruct.ap.test.references.samename.b.CustomMapper.class
})
public interface AnotherSourceTargetMapper {

    AnotherSourceTargetMapper INSTANCE = Mappers.getMapper( AnotherSourceTargetMapper.class );

    Target sourceToTarget(Source source);
}
