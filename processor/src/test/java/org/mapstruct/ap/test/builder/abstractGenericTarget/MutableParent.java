/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.builder.abstractGenericTarget;

public class MutableParent implements Parent<ImmutableChild> {
    private int count;
    private ImmutableChild child;
    private Child nonGenericChild;

    @Override
    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public ImmutableChild getChild() {
        return child;
    }

    public void setChild(ImmutableChild child) {
        this.child = child;
    }

    @Override
    public Child getNonGenericChild() {
        return nonGenericChild;
    }

    public void setNonGenericChild(Child nonGenericChild) {
        this.nonGenericChild = nonGenericChild;
    }
}
