/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.selection.generics;

public class ErroneousSource2 {

    private WildCardExtendsWrapper<TypeA> fooWildCardExtendsTypeAFailure;

    public WildCardExtendsWrapper<TypeA> getFooWildCardExtendsTypeAFailure() {
        return fooWildCardExtendsTypeAFailure;
    }

    public void setFooWildCardExtendsTypeAFailure(WildCardExtendsWrapper<TypeA> fooWildCardExtendsTypeAFailure) {
        this.fooWildCardExtendsTypeAFailure = fooWildCardExtendsTypeAFailure;
    }
}
