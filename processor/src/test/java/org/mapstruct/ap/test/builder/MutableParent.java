package org.mapstruct.ap.test.builder;

import java.util.List;

public class MutableParent {
    private int count;
    private List<MutableSource> children;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<MutableSource> getChildren() {
        return children;
    }

    public void setChildren(List<MutableSource> children) {
        this.children = children;
    }
}
