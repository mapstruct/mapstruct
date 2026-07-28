package org.mapstruct.factory;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

@Mapper(
    uses = MapperWhichIsUsed.class,
    injectionStrategy = InjectionStrategy.CONSTRUCTOR
)
public interface MapperWithUses {
    Bar toModel(BarDto dto);

    class Bar {
        private final String name;
        private final MapperWhichIsUsed.Foo foo;

        public Bar(String name, MapperWhichIsUsed.Foo foo) {
            this.name = name;
            this.foo = foo;
        }
    }

    class BarDto {
        private final String name;
        private final MapperWhichIsUsed.FooDto foo;

        BarDto(String name, MapperWhichIsUsed.FooDto foo) {
            this.name = name;
            this.foo = foo;
        }

        public String getName() {
            return this.name;
        }

        public MapperWhichIsUsed.FooDto getFoo() {
            return this.foo;
        }
    }
}
