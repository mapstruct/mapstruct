/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.selection.generics;

public class TwoArgWrapper<T1, T2> {

    public TwoArgWrapper() {
    }

    public TwoArgWrapper(TwoArgHolder<T1, T2> wrapped) {
        this.wrapped = wrapped;
    }

    private TwoArgHolder<T1, T2> wrapped;

    public TwoArgHolder<T1, T2> getWrapped() {
        return wrapped;
    }

    public void setWrapped(TwoArgHolder<T1, T2> wrapped) {
        this.wrapped = wrapped;
    }
}
