/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._3949;

public class ParentTarget implements ParentTargetInterface {
    private ParentTarget child;

    @Override
    public ParentTarget getChild() {
        return child;
    }

    @Override
    public void setChild(String child) {
        throw new IllegalArgumentException();
    }

    @Override
    public void setChild(ParentTarget child) {
        this.child = child;
    }
}
