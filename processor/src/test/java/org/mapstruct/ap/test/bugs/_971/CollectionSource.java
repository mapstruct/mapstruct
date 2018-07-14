/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._971;

import java.util.List;

/**
 *
 * @author Filip Hrisafov
 */
public class CollectionSource {

    private List<Integer> integers;
    private List<Integer> integersCollection;

    public List<Integer> getIntegers() {
        return integers;
    }

    public void setIntegers(List<Integer> integers) {
        this.integers = integers;
    }

    public List<Integer> getIntegersCollection() {
        return integersCollection;
    }

    public void setIntegersCollection(List<Integer> integersCollection) {
        this.integersCollection = integersCollection;
    }
}
