package org.mapstruct.factory;

class MapperWhichIsUsedImpl implements MapperWhichIsUsed {

    @Override
    public Foo toModel(MapperWhichIsUsed.FooDto dto) {
        if ( dto == null ) {
            return null;
        }

        Foo foo = new Foo();

        return foo;
    }
}
