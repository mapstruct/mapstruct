/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nullcheck.jspecify;

import java.util.Map;

import org.jspecify.annotations.NullMarked;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@NullMarked
@Mapper
public interface JSpecifyMapMethodMapper {

    JSpecifyMapMethodMapper INSTANCE = Mappers.getMapper( JSpecifyMapMethodMapper.class );

    Map<String, NullMarkedTargetBean> mapAll(Map<String, NullMarkedSourceBean> sources);

    NullMarkedTargetBean map(NullMarkedSourceBean source);
}
