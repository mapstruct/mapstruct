/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.selection.generics;

public class ErroneousTarget1 {

    private TypeA fooUpperBoundFailure;

    public TypeA getFooUpperBoundFailure() {
        return fooUpperBoundFailure;
    }

    public void setFooUpperBoundFailure(TypeA fooUpperBoundFailure) {
        this.fooUpperBoundFailure = fooUpperBoundFailure;
    }
}
