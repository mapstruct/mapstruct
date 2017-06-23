package org.mapstruct.ap.test.builder.genericBuilder;

import org.mapstruct.MappedByBuilder;

@MappedByBuilder( builderClass = ThingOne.ThingOneBuilder.class )
public class ThingOne implements AbstractThing {

    private final String foo;
    private final Integer bar;

    public ThingOne(ThingOneBuilder thingOneBuilder) {
        this.foo = thingOneBuilder.foo;
        this.bar = thingOneBuilder.bar;
    }

    public static ThingOneBuilder builder() {
        return new ThingOneBuilder();
    }

    @Override
    public String getFoo() {
        return foo;
    }

    @Override
    public Integer getBar() {
        return bar;
    }

    public static class ThingOneBuilder extends AbstractThingBuilder<ThingOne> {
        private Integer bar;

        public ThingOneBuilder bar(Integer bar) {
            this.bar = bar;
            return this;
        }

        @Override
        ThingOne build() {
            return new ThingOne(this);
        }
    }
}
