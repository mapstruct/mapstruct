/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.builder.abstractGenericTarget;

public class ParentSource {
    private int count;
    private ChildSource child;
    private ChildSource nonGenericChild;

    public ChildSource getNonGenericChild() {
        return nonGenericChild;
    }

    public void setNonGenericChild(ChildSource nonGenericChild) {
        this.nonGenericChild = nonGenericChild;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public ChildSource getChild() {
        return child;
    }

    public void setChild(ChildSource child) {
        this.child = child;
    }
}
