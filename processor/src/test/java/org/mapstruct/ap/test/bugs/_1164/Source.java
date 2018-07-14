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
class Source {

    public static class SourceNested {
    }

    private List<List<SourceNested>> nestedLists;
    private Map<Integer, List<SourceNested>> nestedMaps;
    private GenericHolder<List<SourceNested>> genericHolder;

    public List<List<SourceNested>> getNestedLists() {
        return nestedLists;
    }

    public void setNestedLists(List<List<SourceNested>> nestedLists) {
        this.nestedLists = nestedLists;
    }

    public Map<Integer, List<SourceNested>> getNestedMaps() {
        return nestedMaps;
    }

    public void setNestedMaps(Map<Integer, List<SourceNested>> nestedMaps) {
        this.nestedMaps = nestedMaps;
    }

    public GenericHolder<List<SourceNested>> getGenericHolder() {
        return genericHolder;
    }

    public void setGenericHolder(GenericHolder<List<SourceNested>> genericHolder) {
        this.genericHolder = genericHolder;
    }
}
