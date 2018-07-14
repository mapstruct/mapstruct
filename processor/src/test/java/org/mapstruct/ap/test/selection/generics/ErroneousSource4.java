/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.selection.generics;

public class ErroneousSource4 {

    private WildCardSuperWrapper<TypeA> fooWildCardSuperTypeAFailure;

    public WildCardSuperWrapper<TypeA> getFooWildCardSuperTypeAFailure() {
        return fooWildCardSuperTypeAFailure;
    }

    public void setFooWildCardSuperTypeAFailure(WildCardSuperWrapper<TypeA> fooWildCardSuperTypeAFailure) {
        this.fooWildCardSuperTypeAFailure = fooWildCardSuperTypeAFailure;
    }
}
