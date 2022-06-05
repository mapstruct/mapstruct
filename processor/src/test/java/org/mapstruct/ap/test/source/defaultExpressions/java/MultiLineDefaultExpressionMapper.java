/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.source.defaultExpressions.java;

import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneOffset;
import java.util.Date;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
 * @author Filip Hrisafov
 */
@Mapper(imports = { Date.class, LocalDate.class, ZoneOffset.class, Month.class })
public interface MultiLineDefaultExpressionMapper {

    MultiLineDefaultExpressionMapper INSTANCE = Mappers.getMapper( MultiLineDefaultExpressionMapper.class );

    @Mappings({
        @Mapping(
            target = "sourceId",
            source = "id",
            defaultExpression = "java( new StringBuilder()\n.append( \"test\" )\n.toString() )"
        ),
        @Mapping(
            target = "sourceDate",
            source = "date",
            defaultExpression = "java(" +
                "Date.from(\n" +
                "LocalDate.of( 2022, Month.JUNE, 5 )\n" +
                ".atTime( 17, 10 )\n" +
                ".toInstant( ZoneOffset.UTC )\n)" +
                ")"
        )
    })
    Target sourceToTarget(Source s);
}
