/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.selection.qualifier.errors;

import java.util.Collection;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface ErroneousMessageByNamedWithIterableMapper {

    @Mapping(target = "elements", qualifiedByName = "SelectMe")
    Target map(Source source);

    // CHECKSTYLE:OFF
    class Source {

        public Collection<String> elements;
    }

    class Target {

        public Collection<String> elements;
    }
    // CHECKSTYLE ON

}
