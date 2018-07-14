/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.selection.generics;

import org.mapstruct.ap.test.NoProperties;

public class ErroneousSource6 {

    private WildCardSuperWrapper<NoProperties> foo;

    public WildCardSuperWrapper<NoProperties> getFoo() {
        return foo;
    }

    public void setFoo(WildCardSuperWrapper<NoProperties> foo) {
        this.foo = foo;
    }
}
