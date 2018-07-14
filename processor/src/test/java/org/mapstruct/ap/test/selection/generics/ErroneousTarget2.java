/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.selection.generics;

public class ErroneousTarget2 {

    private TypeA fooWildCardExtendsTypeAFailure;

    public TypeA getFooWildCardExtendsTypeAFailure() {
        return fooWildCardExtendsTypeAFailure;
    }

    public void setFooWildCardExtendsTypeAFailure(TypeA fooWildCardExtendsTypeAFailure) {
        this.fooWildCardExtendsTypeAFailure = fooWildCardExtendsTypeAFailure;
    }

}
