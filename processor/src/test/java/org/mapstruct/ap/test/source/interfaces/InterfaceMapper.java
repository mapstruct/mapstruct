package org.mapstruct.ap.test.source.interfaces;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

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
