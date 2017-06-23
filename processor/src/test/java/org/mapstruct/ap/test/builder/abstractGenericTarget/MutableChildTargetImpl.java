package org.mapstruct.ap.test.builder.abstractGenericTarget;

public class MutableChildTargetImpl implements AbstractChildTarget {
    private String bar;

    @Override
    public String getBar() {
        return null;
    }

    public void setBar(String bar) {
        this.bar = bar;
    }
}
