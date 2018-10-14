/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.source.defaultExpressions.java;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.Date;

/**
 * @author Jeffrey Smyth
 */
@Mapper(imports = { Date.class })
public interface SourceTargetMapper {

    SourceTargetMapper INSTANCE = Mappers.getMapper( SourceTargetMapper.class );

    @Mapping(target = "sourceId", source = "id", defaultExpression = "java( String.valueOf( \"test\" ) )")
    @Mapping(target = "sourceDate", source = "date", defaultExpression = "java( new Date( 30L ))")
    Target sourceToTarget(Source s);
}
