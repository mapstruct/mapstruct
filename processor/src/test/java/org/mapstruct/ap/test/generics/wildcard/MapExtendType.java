/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.generics.wildcard;

import java.util.Map;

public class MapExtendType {

    private Map<? extends SimpleObject, ? extends SimpleObject> simpleMap;

    public Map<? extends SimpleObject, ? extends SimpleObject> getSimpleMap() {
        return simpleMap;
    }

    public void setSimpleMap(
            Map<? extends SimpleObject, ? extends SimpleObject> simpleMap) {
        this.simpleMap = simpleMap;
    }
}
