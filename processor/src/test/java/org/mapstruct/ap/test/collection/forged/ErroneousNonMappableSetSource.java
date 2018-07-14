/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.collection.forged;

import java.util.Set;

public class ErroneousNonMappableSetSource {

    private Set<Foo> nonMappableSet;

    public Set<Foo> getNonMappableSet() {
        return nonMappableSet;
    }

    public void setNonMappableSet(Set<Foo> nonMappableSet) {
        this.nonMappableSet = nonMappableSet;
    }

}
