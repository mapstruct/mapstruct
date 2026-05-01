/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.generics.wildcard;

import java.util.Map;

public class MapSuperType {

    private Map<? super SimpleObject, ? super SimpleObject> simpleMap;

    public Map<? super SimpleObject, ? super SimpleObject> getSimpleMap() {
        return simpleMap;
    }

    public void setSimpleMap(
            Map<? super SimpleObject, ? super SimpleObject> simpleMap) {
        this.simpleMap = simpleMap;
    }
}
