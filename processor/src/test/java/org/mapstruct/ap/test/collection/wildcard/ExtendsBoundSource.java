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
public class ExtendsBoundSource {

    private List<? extends Idea> elements;
    Map<? extends Idea, ? extends Idea> entries;

    public List<? extends Idea> getElements() {
        return elements;
    }

    public void setElements(List<? extends Idea> elements) {
        this.elements = elements;
    }

    public Map<? extends Idea, ? extends Idea> getEntries() {
        return entries;
    }

    public void setEntries(Map<? extends Idea, ? extends Idea> entries) {
        this.entries = entries;
    }

}
