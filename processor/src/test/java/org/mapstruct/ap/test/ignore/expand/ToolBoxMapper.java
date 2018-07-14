/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.ignore.expand;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 *
 * @author Sjaak Derksen
 */
@Mapper
public interface ToolBoxMapper {

    ToolBoxMapper INSTANCE = Mappers.getMapper( ToolBoxMapper.class );

    @BeanMapping(ignoreByDefault = true)
    @Mapping( target = "hammer.description", source = "hammerDescription" )
    ExpandedToolbox expand( FlattenedToolBox toolbox );

}
