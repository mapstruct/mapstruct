/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nullcheck.mapkey;

import java.util.Map;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface NullMapKeyMapper {

    NullMapKeyMapper INSTANCE = Mappers.getMapper( NullMapKeyMapper.class );

    Map<String, String> map(Map<Integer, Integer> source);
}
