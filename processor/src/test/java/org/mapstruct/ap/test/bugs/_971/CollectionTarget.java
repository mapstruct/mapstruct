/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._971;

import java.util.Collection;
import java.util.List;

/**
 *
 * @author Filip Hrisafov
 */
public class CollectionTarget {

    private List<String> integers;
    private Collection<String> integersCollection;

    public List<String> getIntegers() {
        return integers;
    }

    public void setIntegers(List<String> integers) {
        this.integers = integers;
    }

    public Collection<String> getIntegersCollection() {
        return integersCollection;
    }

    public void setIntegersCollection(Collection<String> integersCollection) {
        this.integersCollection = integersCollection;
    }
}
