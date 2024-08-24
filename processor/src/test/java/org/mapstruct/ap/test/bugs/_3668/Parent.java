/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._3668;

public abstract class Parent<T extends Child> {

    private Long id;

    private T child;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public T getChild() {
        return child;
    }

    public void setChild(T child) {
        this.child = child;
    }

    public static class ParentA extends Parent<Child.ChildA> { }

    public static class ParentB extends Parent<Child.ChildB> { }
}
