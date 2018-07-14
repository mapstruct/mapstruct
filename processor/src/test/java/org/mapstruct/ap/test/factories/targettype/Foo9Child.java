/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.factories.targettype;

/**
 * @author Remo Meier
 */
public class Foo9Child extends Foo9Base {

    private String childProp;

    public String getChildProp() {
        return childProp;
    }

    public void setChildProp(String childProp) {
        this.childProp = childProp;
    }
}
