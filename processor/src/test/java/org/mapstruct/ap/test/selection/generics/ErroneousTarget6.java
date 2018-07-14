/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.selection.generics;

public class ErroneousTarget6 {

    private WildCardSuperWrapper<TypeA> foo;

    public WildCardSuperWrapper<TypeA> getFoo() {
        return foo;
    }

    public void setFoo(WildCardSuperWrapper<TypeA> foo) {
        this.foo = foo;
    }
}
