/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.java8stream.context;

import java.util.Collection;
import java.util.stream.Stream;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author Filip Hrisafov
 */
@Mapper(uses = StreamContext.class)
public interface StreamWithContextMapper {

    StreamWithContextMapper INSTANCE = Mappers.getMapper( StreamWithContextMapper.class );

    Collection<String> streamToCollection(Stream<Integer> integerStream);

    Stream<Long> collectionToStream(Collection<Integer> integerCollection);

    String[] streamToArray(Stream<Integer> integerStream);

    Stream<Integer> arrayToStream(Integer[] integers);

    Stream<Integer> streamToStream(Stream<String> stringStream);

    Stream<String> intStreamToStringStream(Stream<Integer> integerStream);
}
