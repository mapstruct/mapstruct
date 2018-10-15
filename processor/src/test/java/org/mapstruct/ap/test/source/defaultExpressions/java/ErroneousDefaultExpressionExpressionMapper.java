/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.source.defaultExpressions.java;

import java.util.Date;
import java.util.UUID;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

/**
 * @author Jeffrey Smyth
 */
@Mapper(imports = { UUID.class, Date.class })
public interface ErroneousDefaultExpressionExpressionMapper {

    @Mappings({
        @Mapping(target = "sourceId", expression = "java( UUID.randomUUID().toString() )",
            defaultExpression = "java( UUID.randomUUID().toString() )"),
        @Mapping(target = "sourceDate", source = "date", defaultExpression = "java( new Date())")
    })
    Target sourceToTarget(Source s);
}
