/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.callbacks.cyclic;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author Ben Zegveld
 */
@Mapper( uses = PreventCyclicContext.class )
public interface CyclicMapper {

    CyclicMapper INSTANCE = Mappers.getMapper( CyclicMapper.class );

    TeacherDto map(Teacher teacher);
}
