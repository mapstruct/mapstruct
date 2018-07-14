/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.collection.erroneous;

import java.util.Date;
import java.util.List;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;

/**
 *
 * @author Sjaak Derksen
 */
@Mapper
public interface EmptyItererableMappingMapper {

    @IterableMapping
    List<String> stringListToDateList(List<Date> dates);
}
