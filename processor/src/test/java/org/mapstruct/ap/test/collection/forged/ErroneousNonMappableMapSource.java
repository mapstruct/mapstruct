/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.collection.forged;

import java.util.Map;

public class ErroneousNonMappableMapSource {

    private Map<Foo, Foo> nonMappableMap;

    public Map<Foo, Foo> getNonMappableMap() {
        return nonMappableMap;
    }

    public void setNonMappableMap(Map<Foo, Foo> nonMappableMap) {
        this.nonMappableMap = nonMappableMap;
    }
}
