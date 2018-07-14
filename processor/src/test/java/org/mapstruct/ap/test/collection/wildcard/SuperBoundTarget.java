/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.collection.wildcard;

import java.util.List;
import java.util.Map;

/**
 *
 * @author Sjaak Derksen
 */
public class SuperBoundTarget {

    private List<? super Plan> elements;
    private Map<? super Plan, ? super Plan> entries;

    public List<? super Plan> getElements() {
        return elements;
    }

    public void setElements(List<? super Plan> elements) {
        this.elements = elements;
    }

    public Map<? super Plan, ? super Plan> getEntries() {
        return entries;
    }

    public void setEntries(Map<? super Plan, ? super Plan> entries) {
        this.entries = entries;
    }
}
