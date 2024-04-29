package org.mapstruct.ap.test.nullcheck.redundant;

public class FooSourceNested {
    private String bar;
    private FooSource nested;

    public FooSource getNested() {
        return nested;
    }

    public void setNested(FooSource nested) {
        this.nested = nested;
    }

    public String getBar() {
        return bar;
    }

    public void setBar(String bar) {
        this.bar = bar;
    }
}
