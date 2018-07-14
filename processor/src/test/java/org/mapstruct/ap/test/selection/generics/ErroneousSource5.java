/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.selection.generics;

public class ErroneousSource5 {

    private WildCardSuperWrapper<TypeC> fooWildCardSuperTypeCFailure;

    public WildCardSuperWrapper<TypeC> getFooWildCardSuperTypeCFailure() {
        return fooWildCardSuperTypeCFailure;
    }

    public void setFooWildCardSuperTypeCFailure(WildCardSuperWrapper<TypeC> fooWildCardSuperTypeCFailure) {
        this.fooWildCardSuperTypeCFailure = fooWildCardSuperTypeCFailure;
    }
}
