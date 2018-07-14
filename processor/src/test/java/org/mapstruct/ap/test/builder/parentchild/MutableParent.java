/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.builder.parentchild;

import java.util.List;

public class MutableParent {
    private int count;
    private List<MutableChild> children;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<MutableChild> getChildren() {
        return children;
    }

    public void setChildren(List<MutableChild> children) {
        this.children = children;
    }
}
