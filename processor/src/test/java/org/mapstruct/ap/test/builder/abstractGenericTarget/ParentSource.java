package org.mapstruct.ap.test.builder.abstractGenericTarget;

public class ParentSource {
    private int count;
    private ChildSource nested;
    private ChildSource nonGenericizedNested;

    public ChildSource getNonGenericizedNested() {
        return nonGenericizedNested;
    }

    public void setNonGenericizedNested(ChildSource nonGenericizedNested) {
        this.nonGenericizedNested = nonGenericizedNested;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public ChildSource getNested() {
        return nested;
    }

    public void setNested(ChildSource nested) {
        this.nested = nested;
    }
}
