/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2111;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import static java.util.Collections.singletonList;

@Mapper
public interface Issue2111Mapper {

    @Mapping(target = "strs", source = "ex", qualifiedByName = "wrap")
    DTO map(UseExample from);

    @Named("wrap")
    default String mapExample(Example ex) {
        return ex.name;
    }

    @Named("wrap")
    default <T> List<T> wrapInList(T t) {
        return singletonList( t );
    }

    //CHECKSTYLE:OFF
    class Example {
        public String name;
    }

    class UseExample {
        public Example ex;
    }

    class DTO {
        public List<String> strs;
    }
    //CHECKSTYLE:ON
}
