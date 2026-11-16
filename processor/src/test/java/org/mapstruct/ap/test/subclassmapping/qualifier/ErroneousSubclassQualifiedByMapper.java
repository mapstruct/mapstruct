/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.subclassmapping.qualifier;

import org.mapstruct.Mapper;
import org.mapstruct.SubclassMapping;

@Mapper(uses = { RossiniMapper.class, VivaldiMapper.class })
public interface ErroneousSubclassQualifiedByMapper {
    @SubclassMapping(source = Rossini.class, target = RossiniDto.class, qualifiedBy = NonExistent.class)
    ComposerDto toDto(Composer composer);
}
