/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.conversion.java8time.custompatterndatetimeformattergenerated;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
 *
 */
@Mapper
public interface SourceTargetMapper {

    String LOCAL_DATE_TIME_FORMAT = "dd.MM.yyyy HH:mm";
    String VERY_SIMILAR_LOCAL_DATE_TIME_FORMAT = "dd.MM.yyyy HH.mm";

    SourceTargetMapper INSTANCE = Mappers.getMapper( SourceTargetMapper.class );

    @Mappings( { @Mapping( target = "localDateTime1", dateFormat = LOCAL_DATE_TIME_FORMAT ),
        @Mapping( target = "localDateTime2", dateFormat = LOCAL_DATE_TIME_FORMAT ),
        @Mapping( target = "localDateTime3", dateFormat = VERY_SIMILAR_LOCAL_DATE_TIME_FORMAT ) } )
    Target sourceToTarget(Source source);

}
