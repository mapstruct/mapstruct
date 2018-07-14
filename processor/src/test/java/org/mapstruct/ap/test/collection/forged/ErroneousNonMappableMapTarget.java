/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.collection.forged;

import java.util.Map;

public class ErroneousNonMappableMapTarget {

    private Map<Bar, Bar> nonMappableMap;

    public Map<Bar, Bar> getNonMappableMap() {
        return nonMappableMap;
    }

    public void setNonMappableMap(Map<Bar, Bar> nonMappableMap) {
        this.nonMappableMap = nonMappableMap;
    }

}
