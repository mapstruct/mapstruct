package org.mapstruct.ap.test.builder.genericBuilder;

public abstract class AbstractThingBuilder<T extends AbstractThing> {

    protected String foo;

    public AbstractThingBuilder<T> foo(String foo) {
        this.foo = foo;
        return this;
    }

    abstract T build();
}
