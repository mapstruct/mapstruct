/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.selection.generics;

public class ErroneousTarget4 {

    private TypeA fooWildCardSuperTypeAFailure;

    public TypeA getFooWildCardSuperTypeAFailure() {
        return fooWildCardSuperTypeAFailure;
    }

    public void setFooWildCardSuperTypeAFailure(TypeA fooWildCardSuperTypeAFailure) {
        this.fooWildCardSuperTypeAFailure = fooWildCardSuperTypeAFailure;
    }

}
