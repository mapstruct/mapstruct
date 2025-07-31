/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.source.interfaces;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * @author Diego Pedregal
 */
@Mapper
public interface InterfaceMapper {

    @Mapping(target = "f", source = "b")
    @Mapping(target = "g", source = "c")
    E map(A a);

    @Mapping(target = "h", source = "d")
    F map(A.B b);

    @Mapping(target = "h", source = "d")
    H map(A.B.D d);

    @Mapping(target = "g", source = "c")
    G map(A.C c);

}
