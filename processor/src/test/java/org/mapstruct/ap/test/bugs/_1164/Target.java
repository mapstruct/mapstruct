/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1164;

import java.util.List;
import java.util.Map;

/**
 * @author Filip Hrisafov
 */
class Target {

    public static class TargetNested {
    }

    public static class MapNested {
    }

    public static class GenericNested {
    }

    private List<List<TargetNested>> nestedLists;
    private Map<String, List<MapNested>> nestedMaps;
    private GenericHolder<List<GenericNested>> genericHolder;

    public List<List<TargetNested>> getNestedLists() {
        return nestedLists;
    }

    public void setNestedLists(List<List<TargetNested>> nestedLists) {
        this.nestedLists = nestedLists;
    }

    public Map<String, List<MapNested>> getNestedMaps() {
        return nestedMaps;
    }

    public void setNestedMaps(Map<String, List<MapNested>> nestedMaps) {
        this.nestedMaps = nestedMaps;
    }

    public GenericHolder<List<GenericNested>> getGenericHolder() {
        return genericHolder;
    }

    public void setGenericHolder(GenericHolder<List<GenericNested>> genericHolder) {
        this.genericHolder = genericHolder;
    }
}
