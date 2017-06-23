package org.mapstruct.ap.test.builder.genericBuilder;

public class ThingTwo {
    private String foo;
    private Integer bar;

    public ThingTwo() {
    }

    public ThingTwo(String foo, Integer bar) {
        this.foo = foo;
        this.bar = bar;
    }

    public String getFoo() {
        return foo;
    }

    public void setFoo(String foo) {
        this.foo = foo;
    }

    public Integer getBar() {
        return bar;
    }

    public void setBar(Integer bar) {
        this.bar = bar;
    }
}
