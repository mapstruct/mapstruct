package org.mapstruct.ap.test.builder.parentchild;

public class MutableChild {
    public MutableChild() {
    }

    public MutableChild(String foo) {
        this.foo = foo;
    }

    private String foo;

    public String getFoo() {
        return foo;
    }

    public void setFoo(String foo) {
        this.foo = foo;
    }
}
