/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._971;

import java.util.Map;

/**
 *
 * @author Filip Hrisafov
 */
public class MapSource {

    private Map<Integer, String> integersSortedMap;
    private Map<Integer, String> integersBaseMap;

    public Map<Integer, String> getIntegersSortedMap() {
        return integersSortedMap;
    }

    public void setIntegersSortedMap(Map<Integer, String> integersSortedMap) {
        this.integersSortedMap = integersSortedMap;
    }

    public Map<Integer, String> getIntegersBaseMap() {
        return integersBaseMap;
    }

    public void setIntegersBaseMap(Map<Integer, String> integersBaseMap) {
        this.integersBaseMap = integersBaseMap;
    }
}
