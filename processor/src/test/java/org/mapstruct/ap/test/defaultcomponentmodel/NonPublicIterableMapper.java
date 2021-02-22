/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.defaultcomponentmodel;

import java.util.List;

import org.mapstruct.Mapper;

/**
 * @author Filip Hrisafov
 */
@Mapper(uses = NonPublicMapper.class)
public interface NonPublicIterableMapper {

    List<Target> map(List<Source> list);
}
