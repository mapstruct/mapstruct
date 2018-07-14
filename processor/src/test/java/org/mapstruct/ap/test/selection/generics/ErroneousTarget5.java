/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.selection.generics;

public class ErroneousTarget5 {

    private TypeC fooWildCardSuperTypeCFailure;

    public TypeC getFooWildCardSuperTypeCFailure() {
        return fooWildCardSuperTypeCFailure;
    }

    public void setFooWildCardSuperTypeCFailure(TypeC fooWildCardSuperTypeCFailure) {
        this.fooWildCardSuperTypeCFailure = fooWildCardSuperTypeCFailure;
    }

}
