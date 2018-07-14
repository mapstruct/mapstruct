/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._971;

import java.util.Map;
import java.util.SortedMap;

/**
 *
 * @author Filip Hrisafov
 */
public class MapTarget {

    private SortedMap<String, Integer> integersSortedMap;
    private Map<String, Integer> integersBaseMap;

    public SortedMap<String, Integer> getIntegersSortedMap() {
        return integersSortedMap;
    }

    public void setIntegersSortedMap(SortedMap<String, Integer> integersSortedMap) {
        this.integersSortedMap = integersSortedMap;
    }

    public Map<String, Integer> getIntegersBaseMap() {
        return integersBaseMap;
    }

    public void setIntegersBaseMap(Map<String, Integer> integersBaseMap) {
        this.integersBaseMap = integersBaseMap;
    }
}
