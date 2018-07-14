/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._513;

import java.util.Collection;
import java.util.Map;

/**
 *
 * @author Sjaak Derksen
 */
public class Source {

    private Collection<SourceElement> collection;
    private Map<SourceKey, SourceValue> map;

    public Collection<SourceElement> getCollection() {
        return collection;
    }

    public void setCollection(Collection<SourceElement> collection) {
        this.collection = collection;
    }

    public Map<SourceKey, SourceValue> getMap() {
        return map;
    }

    public void setMap(Map<SourceKey, SourceValue> map) {
        this.map = map;
    }
}
