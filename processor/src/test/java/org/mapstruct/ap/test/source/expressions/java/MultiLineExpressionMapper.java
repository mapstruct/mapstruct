/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.source.expressions.java;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ap.test.source.expressions.java.mapper.TimeAndFormat;
import org.mapstruct.factory.Mappers;

/**
 * @author Filip Hrisafov
 */
@Mapper(imports = TimeAndFormat.class)
public interface MultiLineExpressionMapper {

    MultiLineExpressionMapper INSTANCE = Mappers.getMapper( MultiLineExpressionMapper.class );

    @Mappings({
        @Mapping(target = "timeAndFormat", expression = "java( new TimeAndFormat(\ns.getTime(),\ns.getFormat()\n ))"),
        @Mapping(target = "anotherProp", ignore = true)
    })
    Target mapUsingMultiLineExpression(Source s);

    @Mappings({
        @Mapping(
            target = "timeAndFormat",
            expression = "    java( new TimeAndFormat(\ns.getTime(),\ns.getFormat()\n ))   "
        ),
        @Mapping(target = "anotherProp", ignore = true)
    })
    Target mapUsingMultiLineExpressionWithLeadingSpaces(Source s);
}
