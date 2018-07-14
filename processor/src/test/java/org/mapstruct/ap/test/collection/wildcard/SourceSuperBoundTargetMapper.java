/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.collection.wildcard;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;


/**
 *
 * @author Sjaak Derksen
 */
@Mapper
public interface SourceSuperBoundTargetMapper {

    SourceSuperBoundTargetMapper STM = Mappers.getMapper( SourceSuperBoundTargetMapper.class );

    SuperBoundTarget map(Source source);

    Plan map(Idea in);

}
