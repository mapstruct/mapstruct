/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.collection.map;

import java.util.Date;
import java.util.Map;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.MapMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(uses = CustomNumberMapper.class)
public interface SourceTargetMapper {

    SourceTargetMapper INSTANCE = Mappers.getMapper( SourceTargetMapper.class );

    @MapMapping(valueDateFormat = "dd.MM.yyyy")
    Map<String, String> longDateMapToStringStringMap(Map<Long, Date> source);

    @InheritInverseConfiguration
    Map<Long, Date> stringStringMapToLongDateMap(Map<String, String> source);

    @MapMapping(valueDateFormat = "dd.MM.yyyy")
    void stringStringMapToLongDateMapUsingTargetParameter(@MappingTarget Map<Long, Date> target,
                                                          Map<String, String> source);

    @MapMapping(valueDateFormat = "dd.MM.yyyy")
    Map<Long, Date> stringStringMapToLongDateMapUsingTargetParameterAndReturn(Map<String, String> source,
                                                                              @MappingTarget Map<Long, Date> target);

    Target sourceToTarget(Source source);

    @InheritInverseConfiguration
    Source targetToSource(Target target);

    Map<Number, Number> intIntToNumberNumberMap(Map<Integer, Integer> source);
}
