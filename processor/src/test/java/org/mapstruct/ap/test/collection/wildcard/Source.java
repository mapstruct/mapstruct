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
public class Source {

    private List<Idea> elements;
    Map<Idea, Idea> entries;

    public List<Idea> getElements() {
        return elements;
    }

    public void setElements(List<Idea> elements) {
        this.elements = elements;
    }

    public Map<Idea, Idea> getEntries() {
        return entries;
    }

    public void setEntries(Map<Idea, Idea> entries) {
        this.entries = entries;
    }

}
