package org.mapstruct.factory;

import org.mapstruct.Mapper;

@Mapper
public interface MapperWhichIsUsed {

    Foo toModel(FooDto dto);

    class Foo {
        public String name;
    }

    class FooDto {
        public String name;
    }
}
