/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.java8stream.erroneous;

import java.util.stream.Stream;

import org.mapstruct.Mapper;

@Mapper
public interface ErroneousStreamToNonStreamMapper {

    Integer stringStreamToInteger(Stream<String> strings);

    Stream<String> integerToStringStream(Integer integer);
}
