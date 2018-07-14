/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.java8stream.erroneous;

import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;

/**
 *
 * @author Filip Hrisafov
 */
@Mapper
public interface EmptyStreamMappingMapper {

    @IterableMapping
    Stream<String> dateStreamToStringStream(Stream<Date> dates);

    @IterableMapping
    Stream<String> dateListToStringStream(List<Date> dates);

    @IterableMapping
    List<String> dateListToStringStream(Stream<Date> dates);
}
