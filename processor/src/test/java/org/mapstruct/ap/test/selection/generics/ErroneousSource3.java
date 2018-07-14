/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.selection.generics;

public class ErroneousSource3 {

    private WildCardExtendsMBWrapper<TypeB> fooWildCardExtendsMBTypeBFailure;

    public WildCardExtendsMBWrapper<TypeB> getFooWildCardExtendsMBTypeBFailure() {
        return fooWildCardExtendsMBTypeBFailure;
    }

    public void setFooWildCardExtendsMBTypeBFailure(WildCardExtendsMBWrapper<TypeB> fooWildCardExtendsMBTypeBFailure) {
        this.fooWildCardExtendsMBTypeBFailure = fooWildCardExtendsMBTypeBFailure;
    }
}
