/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.collection.map;

import java.util.Map;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AnnotatedSourceTargetMapper {

    AnnotatedSourceTargetMapper INSTANCE = Mappers.getMapper( AnnotatedSourceTargetMapper.class );

    @Mapping(target = "targetKey", source = "sourceKey")
    @Mapping(target = "otherTargetKey", source = "nonExistentSourceKey")
    void stringMapToStringMapUsingTargetParameter(@MappingTarget Map<String, String> target,
                                                  Map<String, String> source);

    @Mapping(target = "targetKey", source = "sourceKey")
    @Mapping(target = "otherTargetKey", source = "nonExistentSourceKey")
    Map<String, String> stringMapToStringMapUsingTargetParameterAndReturn(Map<String, String> source,
                                                                          @MappingTarget Map<String, String> target);

    @Mapping(target = "targetKey", source = "sourceKey")
    @Mapping(target = "otherTargetKey", source = "nonExistentSourceKey")
    @Mapping(target = "thisWillBeIgnored", ignore = true)
    Map<String, String> stringMapWithMappingAnnotations(Map<String, String> source);

    @Mapping(target = "targetKey", source = "sourceKey")
    @Mapping(target = "otherTargetKey", source = "nonExistentSourceKey")
    Map<String, String> stringMapWithMappingAnnotationsAndDifferentName(Map<String, String> source);

    @Mapping(target = "thisWillBeIgnored", ignore = true)
    @Mapping(target = "expressionNotSupported", expression = "java(\"const\")")
    Map<String, String> stringMapWithInvalidMappingAnnotations(Map<String, String> source);

    @Mapping(target = "targetKey", source = "sourceKey")
    @Mapping(target = "otherTargetKey", source = "nonExistentSourceKey")
    Map<String, Long> stringLongMapToStringIntegerMapWithMappingAnnotations(Map<String, Integer> source);

    @Mapping(target = "2L", source = "1L")
    @Mapping(target = "1L", source = "2L")
    Map<Long, Long> longMapWithMappingAnnotations(Map<Long, Long> source);
}
