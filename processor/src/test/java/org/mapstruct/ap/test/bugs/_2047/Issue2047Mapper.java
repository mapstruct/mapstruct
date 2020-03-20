/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2047;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface Issue2047Mapper {

    // Implementing the mapping in either of the commented ways produces correct code

    @Mapping(target = "name", defaultExpression = "java(\"DEFAULT\")")
//    @Mapping(target = "name", defaultValue = "DEFAULT")
//    @Mapping(target = "name", source = "name", defaultExpression = "java(\"DEFAULT\")")
    TargetPerson map(SourcePerson source);

}
