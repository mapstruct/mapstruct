/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.collection.erroneous;

import java.util.Date;
import java.util.Map;

import org.mapstruct.MapMapping;
import org.mapstruct.Mapper;

/**
 *
 * @author Sjaak Derksen
 */
@Mapper
public interface EmptyMapMappingMapper {

    @MapMapping
    Map<String, String> longDateMapToStringStringMap(Map<Long, Date> source);

}
