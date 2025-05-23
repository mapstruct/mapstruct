package org.mapstruct.factory;

public class MapperWithUsesImpl implements MapperWithUses {

    private final MapperWhichIsUsed mapperWhichIsUsed;

    public MapperWithUsesImpl(MapperWhichIsUsed mapperWhichIsUsed) {
        this.mapperWhichIsUsed = mapperWhichIsUsed;
    }

    @Override
    public Bar toModel(BarDto dto) {
        if (dto == null) {
            return null;
        }

        String name = null;
        MapperWhichIsUsed.Foo foo = null;

        name = dto.getName();
        foo = mapperWhichIsUsed.toModel(dto.getFoo());

        Bar bar = new Bar(name, foo);

        return bar;
    }
}
