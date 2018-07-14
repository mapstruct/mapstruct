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
public class Target {

    private List<Plan> elements;
    private Map<Plan, Plan> entries;

    public List<Plan> getElements() {
        return elements;
    }

    public void setElements(List<Plan> elements) {
        this.elements = elements;
    }

    public Map<Plan, Plan> getEntries() {
        return entries;
    }

    public void setEntries(Map<Plan, Plan> entries) {
        this.entries = entries;
    }
}
